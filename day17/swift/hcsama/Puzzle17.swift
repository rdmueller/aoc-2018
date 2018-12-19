//
//  Puzzle17.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 17.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


private func PrintGrid(_ grid: inout [[Character]])
{
    for l in grid
    {
        print(String(l))
    }
}

private let passthrough: [Character] = ["|", "."]
private let blockers: [Character] = ["#", "~"]

private func Fill(_ x: Int, _ y: Int, _ grid: inout [[Character]]) -> Bool
{
    var tryAgain = true
    while tryAgain
    {
        tryAgain = false
        grid[y][x] = "|"
        if y == grid.count-1
        {
            return false
        }
        if passthrough.contains(grid[y+1][x])
        { // Continue down
            tryAgain = Fill(x, y+1, &grid)
        }
        else
        { // hit obstacle
            var leftBorder = 0
            for x0 in stride(from: x-1, to: 0, by: -1)
            {
                if !blockers.contains(grid[y+1][x0])
                {
                    leftBorder = x0
                    break
                }
                else if blockers.contains(grid[y][x0])
                {
                    leftBorder = x0+1
                    break
                }
            }
            var rightBorder = grid[0].count-1
            for x0 in x+1 ..< grid[0].count
            {
                if !blockers.contains(grid[y+1][x0])
                {
                    rightBorder = x0
                    break
                }
                else if blockers.contains(grid[y][x0])
                {
                    rightBorder = x0-1
                    break
                }
            }
            if blockers.contains(grid[y+1][leftBorder]) && blockers.contains(grid[y][leftBorder-1]) && blockers.contains(grid[y+1][rightBorder]) && blockers.contains(grid[y][rightBorder+1])
            {
                for x0 in leftBorder ... rightBorder
                {
                    grid[y][x0] = "~"
                }
                return true
            }
            else
            {
                for x0 in leftBorder ... rightBorder
                {
                    grid[y][x0] = "|"
                }
                if !blockers.contains(grid[y+1][leftBorder])
                {
                    tryAgain = Fill(leftBorder, y+1, &grid) || tryAgain
                }
                if !blockers.contains(grid[y+1][rightBorder])
                {
                    tryAgain = Fill(rightBorder, y+1, &grid) || tryAgain
                }
            }
        }
    }
    return false
}

func Puzzle17()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/clay.txt")
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/clay_test.txt")
    let lines: [[String]] = file.components(separatedBy: "\n").dropLast().compactMap({$0.components(separatedBy: [",", "=", "."]).compactMap({$0.trimmingCharacters(in: [" "])})})
    let yMax = lines.reduce(0, {m, s in
        let v = s[0] == "y" ? Int(s[1])! : Int(s[5])!
        return v > m ? v : m
        })
    let yMin = lines.reduce(yMax, {m, s in
        let v = s[0] == "y" ? Int(s[1])! : Int(s[3])!
        return v < m ? v : m
    })
    let xMax = lines.reduce(0, {m, s in
        let v = s[0] == "x" ? Int(s[1])! : Int(s[5])!
        return v > m ? v : m
    })
    let xMin = lines.reduce(xMax, {m, s in
        let v = s[0] == "x" ? Int(s[1])! : Int(s[3])!
        return v < m ? v : m
    })
    var grid: [[Character]] = Array(repeating: Array(repeating: ".", count: xMax-xMin+3), count: yMax-yMin+1)
    for l in lines
    {
        if l[0] == "x"
        {
            for y in Int(l[3])! ... Int(l[5])!
            {
                grid[y-yMin][Int(l[1])!-xMin+1] = "#"
            }
        }
        else
        {
            for x in Int(l[3])! ... Int(l[5])!
            {
                grid[Int(l[1])!-yMin][x-xMin+1] = "#"
            }
        }
    }
    let spring = 500-xMin+1
    _ = Fill(spring, 0, &grid)
    PrintGrid(&grid)
    let water: [Character] = ["|", "~"]
    print("17-01:", grid.reduce(0, {r, l in r + l.reduce(0, {$0 + (water.contains($1) ? 1 : 0)})}))
    print("17-02:", grid.reduce(0, {r, l in r + l.reduce(0, {$0 + ($1 == "~" ? 1 : 0)})}))
}
