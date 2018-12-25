//
//  Puzzle25.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 25.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


private struct Point4D : Hashable
{
    var coord: [Int]
    var x: Int {get{return coord[0]}}
    var y: Int {get{return coord[1]}}
    var z: Int {get{return coord[2]}}
    var t: Int {get{return coord[3]}}

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

    static func +(_ lhs: Point4D, _ rhs: Point4D) -> Point4D
    {
        var hlp = lhs.coord
        for i in 0..<hlp.count
        {
            hlp[i] += rhs.coord[i]
        }
        return Point4D(hlp)
    }

    static func -(_ lhs: Point4D, _ rhs: Point4D) -> Point4D
    {
        var hlp = lhs.coord
        for i in 0..<hlp.count
        {
            hlp[i] -= rhs.coord[i]
        }
        return Point4D(hlp)
    }

}


func Puzzle25()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/constellations.txt")
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/constellations4_test.txt")
    let lines: [String] = file.components(separatedBy: "\n").dropLast().filter {_ in true}
    let points = lines.map({Point4D($0.components(separatedBy: ",").map({Int($0)!}))})
    var constellations: [[Point4D]] = []
    let dist = 3
    for y in 0..<points.count
    {
        var placed: [Int] = []
        for c in 0..<constellations.count
        {
            if constellations[c].reduce(0, {$0 + (($1 - points[y]).Manhatten() <= dist ? 1 : 0)}) > 0
            {
                placed.append(c)
            }
        }
        if placed.count > 0
        {
            constellations[placed[0]].append(points[y])
            for c in placed.dropFirst().reversed()
            {
                constellations[placed[0]].append(contentsOf: constellations[c])
                constellations.remove(at: c)
            }
        }
        else
        {
            constellations.append([points[y]])
        }
    }
    print("25-01:", constellations.count)
    print("25-02:", "Automatic")
}
