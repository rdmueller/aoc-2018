//
//  Puzzle23.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 23.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


private struct Point3D
{
    var coord: [Int]
    var x: Int {get{return coord[0]}}
    var y: Int {get{return coord[1]}}
    var z: Int {get{return coord[2]}}

    func Manhatten() -> Int
    {
        return coord.reduce(0, {$0 + abs($1)})
    }

    init()
    {
        self.init([0,0,0])
    }

    init(_ c: [Int])
    {
        coord = c
    }

    static func +(_ lhs: Point3D, _ rhs: Point3D) -> Point3D
    {
        var hlp = lhs.coord
        for i in 0..<hlp.count
        {
            hlp[i] += rhs.coord[i]
        }
        return Point3D(hlp)
    }

    static func -(_ lhs: Point3D, _ rhs: Point3D) -> Point3D
    {
        var hlp = lhs.coord
        for i in 0..<hlp.count
        {
            hlp[i] -= rhs.coord[i]
        }
        return Point3D(hlp)
    }

    static func *(_ lhs: Point3D, _ rhs: Point3D) -> Point3D
    {
        var hlp = lhs.coord
        for i in 0..<hlp.count
        {
            hlp[i] *= rhs.coord[i]
        }
        return Point3D(hlp)
    }
}

private class Bot: Comparable, Hashable
{
    private static var botID = 0
    var pos: Point3D
    var range: Int
    var overlaps: [Bot]
    var id: Int

    init(pos p: Point3D, range r: Int)
    {
        pos = p
        range = r
        overlaps = []
        id = Bot.botID
        Bot.botID += 1
    }

    static func < (lhs: Bot, rhs: Bot) -> Bool
    {
        return lhs.id < rhs.id
    }

    static func == (lhs: Bot, rhs: Bot) -> Bool
    {
        return lhs.id == rhs.id
    }

    func hash(into hasher: inout Hasher)
    {
        hasher.combine(id)
    }
}

private func ScanBestSpot(_ bots: inout [Bot]) -> Point3D
{
    var minP: Point3D = bots[0].pos
    var maxP: Point3D = bots[0].pos

    for b in bots
    {
        for i in 0..<minP.coord.count
        {
            minP.coord[i] = min(b.pos.coord[i], minP.coord[i])
            maxP.coord[i] = max(b.pos.coord[i], maxP.coord[i])
        }
    }
    var bestPos: Point3D = Point3D()
    var nBots = 0
    var stepSize = Point3D()
    let nSteps = 5
    while true
    {
        for i in 0..<stepSize.coord.count
        {
            stepSize.coord[i] = (maxP.coord[i]-minP.coord[i]+1)/nSteps
        }
        if stepSize.Manhatten() == 0
        {
            break
        }
        for z in 0..<nSteps
        {
            for y in 0..<nSteps
            {
                for x in 0..<nSteps
                {
                    let thePos = minP+stepSize*Point3D([x, y, z])
                    let rangeBots = bots.reduce(0, {$0 + (($1.pos-thePos).Manhatten() <= $1.range ? 1 : 0)})
                    if rangeBots > nBots || (rangeBots == nBots && (thePos-Point3D()).Manhatten() < (bestPos-Point3D()).Manhatten())
                    {
                        nBots = rangeBots
                        bestPos = thePos
                        print("Found", nBots, "at", bestPos.x, bestPos.y, bestPos.z)
                    }
                }
            }
        }
        minP = bestPos-stepSize
        maxP = bestPos+stepSize
    }
    return bestPos
}

func Puzzle23()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/bots.txt")
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/bots_test.txt")
    let lines: [String] = file.components(separatedBy: "\n").dropLast().filter {_ in true}
    var bots: [Bot] = lines.map
                        {l in
                            let b = l.components(separatedBy: ["<", ">", "=", ","])
                            return Bot(pos: Point3D([Int(b[2])!, Int(b[3])!, Int(b[4])!]), range: Int(b[7])!)
                        }
    let maxRangeBot = bots.max(by: {$0.range < $1.range})!
    let inRange = bots.filter({($0.pos-maxRangeBot.pos).Manhatten() <= maxRangeBot.range})
    print("23-01:", inRange.count)
    let point = ScanBestSpot(&bots)
    print("23-02:", point.Manhatten())
}
