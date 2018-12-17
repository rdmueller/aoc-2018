//
//  Puzzle15.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 15.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


let dirPoints:[[Point]] =
    [[Point(0, -1), Point(-1, 0), Point(1, 0)],
     [Point(-1, 0), Point(0, -1), Point(0, 1)],
     [Point(1, 0), Point(0, -1), Point(0, 1)],
     [Point(0, 1), Point(-1, 0), Point(1, 0)]]


struct Point: Comparable
{
    var x: Int
    var y: Int

    init(_ x0: Int, _ y0: Int)
    {
        x = x0
        y = y0
    }

    func InRangeOf(_ p: Point) -> Bool
    {
        return (y == p.y && (x == p.x-1 || x == p.x+1)) ||
            (x == p.x && (y == p.y-1 || y == p.y+1))
    }

    func PointsInRange() -> [Point]
    {
        return [Point(x, y-1), Point(x-1, y), Point(x+1, y), Point(x, y+1)]
    }

    func Distance(_ p:Point) -> Int
    {
        return abs(p.x-x)+abs(p.y-y)
    }

    func Direction(_ p:Point) -> Int
    {
        let hlp = Point((p.x-x).signum(), (p.y-y).signum())
        return dirPoints.firstIndex(where: {$0[0] == hlp})!
    }

    static func < (lhs: Point, rhs: Point) -> Bool
    {
        return lhs.y < rhs.y ? true : lhs.y == rhs.y ? lhs.x < rhs.x : false
    }

    static func + (lhs:Point, rhs: Point) -> Point
    {
        return Point(lhs.x+rhs.x, lhs.y+rhs.y)
    }
}

enum FighterType
{
    case elf
    case gob
}

class Fighter: Comparable
{
    let attackPower:Int
    var hitPoints = 200
    var location:Point
    private static var pIDs = 0
    let fighterID = pIDs
    let fighterType:FighterType
    var paths: [[Point]] = []
    var minPathLen = 0

    init(_ p: Point, _ f: FighterType, _ a: Int)
    {
        location = p
        fighterType = f
        attackPower = a
        Fighter.pIDs += 1
    }

    func InRangeOf(_ p: Point) -> Bool
    {
        return location.InRangeOf(p)
    }

    func Attacked(_ aP: Int)
    {
        hitPoints -= aP
    }

    func Move(_ newPos: Point, _ maze: inout [[Character]])
    {
        let hlp = maze[location.y][location.x]
        maze[location.y][location.x] = "."
        location = newPos
        maze[location.y][location.x] = hlp
    }

    func Move(_ dir: Int, _ maze: inout [[Character]])
    {
        Move(location+dirPoints[dir][0], &maze)
    }

    func Die(_ maze: inout [[Character]])
    {
        maze[location.y][location.x] = "."
    }

    func Description() -> String
    {
        return (fighterType == .elf ? "E(" : "G(") + String(hitPoints) + ")"
    }

    static func < (lhs: Fighter, rhs: Fighter) -> Bool
    {
        return lhs.location < rhs.location
    }

    static func == (lhs: Fighter, rhs: Fighter) -> Bool
    {
        return lhs.fighterID == rhs.fighterID
    }

}

func SetDist(_ p: Point, _ dist: Int, _ minDist: Int, _ grid: inout [[Int]], _ maze: inout [[Character]])
{
    if maze[p.y][p.x] == "." && dist <= minDist && (grid[p.y][p.x] > dist)
    {
        grid[p.y][p.x] = dist
        for z in p.PointsInRange()
        {
            SetDist(z, dist+1, minDist, &grid, &maze)
        }
    }

}

func PrintMaze(_ maze: inout [[Character]], _ fighters: inout [Fighter])
{
    for y in 0..<maze.count
    {
        let f = fighters.sorted().filter({$0.location.y == y}).reduce("", {$0 + $1.Description() + " "})
        print(String(maze[y]), f)
    }
}

func RunBattle(_ elfAttackPower: Int) -> Int
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/battle.txt")
    var maze:[[Character]] = file.components(separatedBy: "\n").dropLast().map() { $0.filter() {_ in true} }  // converts to array of char rather than strings
    var fighters: [Fighter] = []
    let exitOnElfDeath: Bool = elfAttackPower != 3
    // Find fighters
    for y in 0..<maze.count
    {
        for x in 0..<maze[y].count
        {
            switch maze[y][x]
            {
            case "E":
                fighters.append(Fighter(Point(x, y), .elf, elfAttackPower))
            case "G":
                fighters.append(Fighter(Point(x, y), .gob, 3))
            default:
                break
            }
        }
    }
    var rounds = 0
    let infiDist = 9999
    roundLoop: while fighters.filter({ $0.fighterType == .elf }).count > 0 && fighters.filter({ $0.fighterType == .gob }).count > 0
    {
        print(".", terminator: "")
        fighters.sort()
        for f in fighters
        {
            if f.hitPoints <= 0 // Dead fighters only invisible after ending this loop
            {
                continue
            }
            let enemyType:FighterType = f.fighterType == .elf ? .gob : .elf
            var inRangeEnemies: [Fighter] = fighters.filter({$0.fighterType == enemyType && $0.InRangeOf(f.location)})
            if inRangeEnemies.count == 0
            {  // Move
                let inRangePoints = fighters.filter({$0.fighterType == enemyType}).flatMap({$0.location.PointsInRange().filter({maze[$0.y][$0.x] == "."})}).sorted()
                var paths: [Int] = Array(repeating: infiDist, count: 4)
                var minDist = infiDist
                var grid: [[Int]] = Array(repeating: Array(repeating: infiDist, count: maze[0].count), count: maze.count)
                for p in inRangePoints
                {
                    SetDist(p, 0, minDist, &grid, &maze)
                    let newPaths = f.location.PointsInRange().map({grid[$0.y][$0.x]})
                    if let m = newPaths.min()
                    {
                        if m < minDist
                        {
                            paths = newPaths
                            minDist = m
                        }
                    }
                }
                if minDist < infiDist
                {
                    f.Move(paths.firstIndex(of: minDist)!, &maze)
                }
            }
            inRangeEnemies = fighters.filter({$0.fighterType == enemyType && $0.InRangeOf(f.location)})
            if inRangeEnemies.count != 0
            {  // Fight
                inRangeEnemies.sort()
                let target = inRangeEnemies.min(by: {$0.hitPoints < $1.hitPoints})!
                target.Attacked(f.attackPower)
                if target.hitPoints <= 0
                {
                    target.Die(&maze)
                    fighters.remove(at: fighters.firstIndex(of: target)!)
                    if exitOnElfDeath && target.fighterType == .elf
                    {
                        print(".")
                        return -1
                    }
                }
            }

            if f != fighters.last! && fighters.filter({ $0.fighterType == enemyType }).count == 0
            {
                break roundLoop
            }
        }
        rounds += 1
    }
    print(".")
    return rounds * fighters.reduce(0, {$0 + $1.hitPoints})
}

func Puzzle15()
{
    print("15-01:", RunBattle(3))
    for i in 4 ..< 100
    {
        let result = RunBattle(i)
        if result > 0
        {
            print("15-02:", result)
            break
        }
    }
}
