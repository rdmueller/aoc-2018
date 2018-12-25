//
//  Puzzle20.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 20.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


struct Coord: Hashable
{
    var x: Int
    var y: Int

    static func +(lhs: Coord, rhs: Coord) -> Coord
    {
        return Coord(x: lhs.x+rhs.x, y: lhs.y + rhs.y)
    }

    static func == (lhs: Coord, rhs: Coord) -> Bool
    {
        return lhs.x == rhs.x && lhs.y == rhs.y
    }

    func hash(into hasher: inout Hasher)
    {
        hasher.combine(x)
        hasher.combine(y)
    }
}

private var surround = [Coord(x: 0, y: -1), Coord(x: -1, y: 0), Coord(x: 1, y: 0), Coord(x: 0, y: 1)]

private func FillDist(_ grid: inout [[Character]], _ dist: inout [[Int]], _ coord: Coord, _ curDist: Int)
{
    if curDist < dist[coord.y][coord.x]
    {
        dist[coord.y][coord.x] = curDist
    }
    else if curDist > dist[coord.y][coord.x]
    {
        return
    }
    for c in surround
    {
        let wall = coord + c
        let newCoord = wall + c
        if grid[wall.y][wall.x] != "#"
        {
            FillDist(&grid, &dist, newCoord, curDist+1)
        }
    }
}

private func EnlargeGrid(_ grid: inout [[Character]], _ byLeft: Int, _ byRight: Int, _ byTop: Int, _ byBottom: Int)
{
    if byLeft > 0
    {
        let hlp: [Character] = Array(repeating: " ", count: byLeft)
        for i in 0..<grid.count
        {
            grid[i].insert(contentsOf: hlp, at: 0)
        }
    }
    if byRight > 0
    {
        let hlp: [Character] = Array(repeating: " ", count: byRight)
        for i in 0..<grid.count
        {
            grid[i].append(contentsOf: hlp)
        }
    }
    if byTop > 0
    {
        let hlp: [[Character]] = Array(repeating: Array(repeating: " ", count: grid[0].count), count: byTop)
        grid.insert(contentsOf: hlp, at: 0)
    }
    if byBottom > 0
    {
        let hlp: [[Character]] = Array(repeating: Array(repeating: " ", count: grid[0].count), count: byBottom)
        grid.append(contentsOf: hlp)
    }
}

private var xMin = 0
private var xMax = 0
private var yMin = 0
private var yMax = 0

private func SetField(_ grid: inout [[Character]], _ c: Character, _ x: Int, _ y: Int, _ xOff: inout Int, _ yOff: inout Int)
{
    let pref = 5
    if x+xOff < 0
    {
        let off = abs(x + xOff)+pref
        EnlargeGrid(&grid, off, 0, 0, 0)
        xOff += off
    }
    else if x+xOff >= grid[0].count
    {
        let off = x+xOff - grid[0].count + 1 + pref
        EnlargeGrid(&grid, 0, off, 0, 0)
    }
    if y+yOff < 0
    {
        let off = abs(y+yOff)+pref
        EnlargeGrid(&grid, 0, 0, off, 0)
        yOff += off
    }
    else if y+yOff >= grid.count
    {
        let off = y+yOff - grid.count + 1 + pref
        EnlargeGrid(&grid, 0, 0, 0, off)
    }
    if [" ", "?"].contains(grid[y+yOff][x+xOff]) || c == "X"
    {
        grid[y+yOff][x+xOff] = c
    }
}

private func FindMatchingParen(_ regex: [Character], _ regInd: Int) -> Int
{
    var nesting = 1
    var ind = regInd
    while nesting > 0
    {
        ind += 1
        switch regex[ind]
        {
        case "(":
            nesting += 1
        case ")":
            nesting -= 1
        default:
            break
        }
    }
    return ind
}

private func BranchParts(_ regex: [Character], _ start: Int, _ end: Int) -> [[Character]]
{
    var parts: [[Character]] = []
    var nesting = 0
    var lastStart = start
    for i in start...end
    {
        switch regex[i]
        {
        case "(":
            nesting += 1
        case ")":
            nesting -= 1
            if nesting == -1  // Final piece
            {
                parts.append(Array(regex[lastStart..<i]))
            }
        case "|":
            if nesting == 0     // Ignore nested parts
            {
                parts.append(Array(regex[lastStart..<i]))
                lastStart = i + 1
            }
        default:
            break
        }
    }
    return parts
}

private func EvaluateRegex(_ grid: inout [[Character]], _ regex: [Character], _ regInd: Int, _ x0: Int, _ y0: Int, _ xOff: inout Int, _ yOff: inout Int) -> Set<Coord>
{
    var ind = regInd
    var x = x0
    var y = y0
    while ind < regex.count
    {
        SetField(&grid, ".", x, y, &xOff, &yOff)
        switch regex[ind]
        {
        case "(":
            let matchingParen = FindMatchingParen(regex, ind)
            let parts = BranchParts(regex, ind+1, matchingParen)
            var coord = Set<Coord>()
            for part in parts
            {
                let newRegex: [Character] = part
                coord = coord.union(EvaluateRegex(&grid, newRegex, 0, x, y, &xOff, &yOff))
            }
            var myCoord = Set<Coord>()
            for c in coord
            {
                let newRegex: [Character] = Array(regex[matchingParen+1..<regex.endIndex])
                myCoord = myCoord.union(EvaluateRegex(&grid, newRegex, 0, c.x, c.y, &xOff, &yOff))
            }
            return myCoord
        case ")":
            print("This should never happen")
        case "|":
            print("This should never happen")
        case "$":
            return Set<Coord>()
        case "N":
            SetField(&grid, "#", x-1, y-1, &xOff, &yOff)
            SetField(&grid, "#", x+1, y-1, &xOff, &yOff)
            SetField(&grid, "-", x, y-1, &xOff, &yOff)
            y = y-2
        case "S":
            SetField(&grid, "#", x-1, y+1, &xOff, &yOff)
            SetField(&grid, "#", x+1, y+1, &xOff, &yOff)
            SetField(&grid, "-", x, y+1, &xOff, &yOff)
            y = y+2
        case "E":
            SetField(&grid, "#", x+1, y-1, &xOff, &yOff)
            SetField(&grid, "#", x+1, y+1, &xOff, &yOff)
            SetField(&grid, "|", x+1, y, &xOff, &yOff)
            x = x+2
        case "W":
            SetField(&grid, "#", x-1, y-1, &xOff, &yOff)
            SetField(&grid, "#", x-1, y+1, &xOff, &yOff)
            SetField(&grid, "|", x-1, y, &xOff, &yOff)
            x = x-2
        default:
            print("This should never happen")
        }
        ind += 1
    }
    return Set<Coord>([Coord(x: x, y: y)])
}

private func BuildMaze(_ grid: inout [[Character]], _ regex: [Character]) -> Coord
{
    let x = grid.count/2
    let y = grid.count/2
    xMax = x
    xMin = x
    yMax = x
    yMax = x
    var xOff = 0
    var yOff = 0
    _ = EvaluateRegex(&grid, regex, 1, x, y, &xOff, &yOff)
    // Shrink grid
    while grid[0].reduce(0, {$0 + ($1 == " " ? 1 : 0)}) == grid[0].count
    {
        grid.removeFirst()
        yOff -= 1
    }
    while grid[grid.endIndex-1].reduce(0, {$0 + ($1 == " " ? 1 : 0)}) == grid[grid.endIndex-1].count
    {
        grid.removeLast()
    }
    while grid.reduce(0, {r, l in r + (l.first! == " " ? 1 : 0)}) == grid.count
    {
        for i in 0..<grid.count
        {
            grid[i].removeFirst()
        }
        xOff -= 1
    }
    while grid.reduce(0, {r, l in r + (l.last! == " " ? 1 : 0)}) == grid.count
    {
        for i in 0..<grid.count
        {
            grid[i].removeLast()
        }
    }
    // Remove ?s
    // and fix outside walls
    for i in 0..<grid.count
    {
        grid[i] = grid[i].map {$0 == "?" || $0 == " " || i == 0 || i == grid.count-1 ? "#" : $0}
    }
    SetField(&grid, "X", x, y, &xOff, &yOff)
    return Coord(x: x+xOff, y: y+yOff)
    //PrintGrid(&grid)
}

func Puzzle20()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/regex.txt")
    //let file = "^ENNWSWW(NEWS|)SSSEEN(WNSE|)EE(SWEN|)NNN$\n"
    //let file = "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$\n"
    //let file = "^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$\n"
    let regex: [Character] = file.dropLast().compactMap({$0})
    var grid: [[Character]] = Array(repeating: Array(repeating: " ", count: 100), count: 100)
    let startingPoint = BuildMaze(&grid, regex)
    let infiniDist = 999999
    var dist: [[Int]] = Array(repeating: Array(repeating: infiniDist, count: grid[0].count), count: grid.count)
    FillDist(&grid, &dist, startingPoint, 0)
    print("20-01:", dist.reduce(0, {r, l in l.reduce(r, {($1 != infiniDist &&  $1 > $0) ? $1 : $0})}))
    print("20-02:", dist.reduce(0, {r, l in l.reduce(r, {$0 + ($1 != infiniDist &&  $1 >= 1000 ? 1 : 0)})}))
}
