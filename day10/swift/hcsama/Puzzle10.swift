//
//  Puzzle8.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 08.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


class Star
{
    var x: Int
    var y: Int
    var vX: Int
    var vY: Int

    init(_ x0: Int, _ y0: Int, _ vX0: Int, _ vY0: Int)
    {
        x = x0
        y = y0
        vX = vX0
        vY = vY0
    }

    func CalcNewPos()
    {
        x += vX
        y += vY
    }

    func RevertPos()
    {
        x -= vX
        y -= vY
    }
}


func CalcBoundingBox(_ stars: [Star]) -> [Int]
{
    var retVal: [Int] = Array(repeating: 0, count: 4)
    retVal[0] = stars.reduce(stars[0].x, { x, s in s.x < x ? s.x : x })
    retVal[1] = stars.reduce(stars[0].x, { x, s in s.x > x ? s.x : x })
    retVal[2] = stars.reduce(stars[0].y, { y, s in s.y < y ? s.y : y })
    retVal[3] = stars.reduce(stars[0].y, { y, s in s.y > y ? s.y : y })
    return retVal
}


func CalcArea(_ stars: [Star]) -> Int
{
    let box = CalcBoundingBox(stars)
    return(box[1] - box[0] + 1) * (box[3] - box[2] + 1)
}


func PrintStars(_ stars: [Star])
{
    let box = CalcBoundingBox(stars)
    var screen = Array(repeating: Array(repeating: " ", count: box[1]-box[0]+1), count: box[3]-box[2]+1)
    for s in stars
    {
        screen[s.y-box[2]][s.x-box[0]] = "X"
    }
    for s in screen
    {
        print(s.joined())
    }
    print("-------------------")
}


func Puzzle10()
{
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/stars_test.txt")
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/stars.txt")
    let lines = file.components(separatedBy: "\n").dropLast()
    let stars: [Star] = lines.map()
    { l in
        let pos: String = String(l[l.index(after: l.firstIndex(of: "<")!) ..< l.firstIndex(of: ">")!])
        let vel: String = String(l[l.index(after: l.lastIndex(of: "<")!) ..< l.lastIndex(of: ">")!])
        return Star(Int(pos.components(separatedBy: ",")[0].trimmingCharacters(in: [" "]))!,
                    Int(pos.components(separatedBy: ",")[1].trimmingCharacters(in: [" "]))!,
                    Int(vel.components(separatedBy: ",")[0].trimmingCharacters(in: [" "]))!,
                    Int(vel.components(separatedBy: ",")[1].trimmingCharacters(in: [" "]))!)
    }
    var area = CalcBoundingBox(stars)
    var found = false
    var timeIndex = 0
    while !found
    {
        // PrintStars(stars)
        for s in stars
        {
            s.CalcNewPos()
        }
        let newArea = CalcBoundingBox(stars)
        if newArea[3]-newArea[2] < area[3]-newArea[2]
        {
            area = newArea
            timeIndex += 1
       }
        else
        {
            found = true
            for s in stars
            {
                s.RevertPos()
            }
        }
    }
    PrintStars(stars)
    print("09-01:", "see above")
    print("09-02:", timeIndex)
}
