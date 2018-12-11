//
//  Puzzle11.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 11.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation

let size = 300
var grid: [[Int8]] = Array(repeating: Array(repeating: 0, count: size), count: size)

func FindLargest(_ sqSize: Int) -> [Int]
{
    var maxPowerLevel = 0
    var xMax = 0
    var yMax = 0
    for y in 0 ..< size - (sqSize-1)
    {
        for x in 0 ..< size - (sqSize-1)
        {
            var sum = 0
            for j in y..<y+sqSize
            {
                sum += Int(grid[j][x..<x+sqSize].reduce(0, { $0+$1 }))
            }
            if sum > maxPowerLevel
            {
                maxPowerLevel = sum
                xMax = x
                yMax = y
            }
        }
    }
    return [xMax, yMax, maxPowerLevel]
}


struct SquareInfo
{
    var x: Int
    var y: Int
    var size: Int
    var power: Int
}

let threadCount = 5
var allSquares: [[SquareInfo]] = Array(repeating: [], count: threadCount)


class FinderThread: Thread
{
    let yRange: Range<Int>
    let threadNo: Int

    init(_ tN: Int, _ yR: Range<Int>)
    {
        yRange = yR
        threadNo = tN
        super.init()
    }

    override func main()
    {
        var squares: [SquareInfo] = []
        for y in yRange
        {
            for x in 0 ..< size-1
            {
                let sqMax = [size - y, size - x].min()!
                var runSum = Int(grid[y][x])
                squares.append(SquareInfo(x: x, y: y, size: 1, power: runSum))
                for sq in 1 ..< sqMax
                {
                    for j in y ... y+sq
                    {
                        runSum += Int(grid[j][x+sq])
                    }
                    for i in x ..< x+sq
                    {
                        runSum += Int(grid[y+sq][i])
                    }
                    squares.append(SquareInfo(x: x, y: y, size: sq+1, power: runSum))
                }
            }
            print(".", terminator: "")
        }
        allSquares[threadNo].append(contentsOf: squares)
    }

}


func Puzzle11()
{
    let serialNumber = 1309

    for y in 0 ..< size
    {
        for x in 0 ..< size
        {
            var p: Int  = ((x + 11) * (y + 1) + serialNumber) * (x + 11)
            p = p % 1000
            p = p / 100 - 5
            grid[y][x] = Int8(p)
        }
    }
    let result1 = FindLargest(3)
    print("11-01:", result1[2], result1[0]+1, result1[1]+1)

    var threads: [FinderThread?] = Array(repeating: nil, count: threadCount)
    var y: Int = 0
    while y < size-1
    {
        while threads.reduce(false, { r, t in r || (t != nil ? t!.isExecuting : false) })
        {
            sleep(1)
        }
        for t in 0..<threadCount
        {
            if y < size-1 && (threads[t] == nil || threads[t]!.isFinished)
            {
            threads[t] = FinderThread(t, y ..< y+1)
            y += 1
            threads[t]!.start()
            }
        }
    }
    print()
    let biggest = allSquares.reduce(
        SquareInfo(x: 0, y: 0, size:0, power: 0),
                    {r, s in [r, s.max(by: { $0.power < $1.power })!].max(by: {$0.power < $1.power})! })
    print("11-02:", biggest.power, biggest.x+1, biggest.y+1, biggest.size)
}
