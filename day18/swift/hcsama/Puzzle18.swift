//
//  Puzzle18.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 18.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func ProcessRound(_ grid: inout [[[Character]]], _ master: Int)
{
    let open = 0
    let trees = 1
    let lumbery = 2
    let xSize = grid[0][0].count
    let ySize = grid[0].count
    for y in 0..<ySize
    {
        for x in 0..<xSize
        {
            var counts = [0, 0, 0]
            for y0 in max(y-1, 0) ... min(y+1, ySize-1)
            {
                for x0 in max(x-1, 0) ... min(x+1, xSize-1)
                {
                    if !(x0 == x && y0 == y)
                    {
                        switch grid[master][y0][x0]
                        {
                        case ".":
                            counts[open] += 1
                        case "|":
                            counts[trees] += 1
                        case "#":
                            counts[lumbery] += 1
                        default:
                            break
                        }
                    }
                }
            }
            var result = grid[master][y][x]
            switch result
            {
            case ".":
                if counts[trees] >= 3
                {
                    result = "|"
                }
            case "|":
                if counts[lumbery] >= 3
                {
                    result = "#"
                }
            case "#":
                if !(counts[lumbery] > 0 && counts[trees] > 0)
                {
                    result = "."
                }
            default:
                break
            }
            grid[(master+1)%2][y][x] = result
        }
    }
}

func PrintTrees(_ grid: [[Character]])
{
    for l in grid
    {
        print(String(l))
    }
}

func Puzzle18()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/trees.txt")
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/trees_test.txt")
    let initial_grid:[[Character]] = file.components(separatedBy: "\n").dropLast().map() { $0.filter() {_ in true} }  // converts to array of char rather than strings
    var grid: [[[Character]]] = [initial_grid, initial_grid]
    var master = 0
    for _ in 0..<10
    {
        ProcessRound(&grid, master)
        master = (master+1)%2
    }
    PrintTrees(grid[master])
    let nTrees = grid[master].reduce(0, {r, l in l.reduce(r, { $0 + ($1 == "|" ? 1 : 0)})})
    let nLumber = grid[master].reduce(0, {r, l in l.reduce(r, { $0 + ($1 == "#" ? 1 : 0)})})
    print("18-01:", nTrees * nLumber)
    var repeaterFound = -1
    var round = 0
    master = 0
    grid = [initial_grid, initial_grid]
    var grids: [[[Character]]] = [initial_grid]
    let manyMinutes = 1000000000
    while repeaterFound < 0 && round < manyMinutes
    {
        ProcessRound(&grid, master)
        master = (master+1)%2
        round += 1
        if let found = grids.firstIndex(of: grid[master])
        {
            repeaterFound = found
        }
        else
        {
            grids.append(grid[master])
        }
    }
    let repeatSize = round - repeaterFound
    let targetRound = (manyMinutes - repeaterFound) % repeatSize + repeaterFound
    let nTrees2 = grids[targetRound].reduce(0, {r, l in l.reduce(r, { $0 + ($1 == "|" ? 1 : 0)})})
    let nLumber2 = grids[targetRound].reduce(0, {r, l in l.reduce(r, { $0 + ($1 == "#" ? 1 : 0)})})
    print("18-02:", nTrees2*nLumber2)
}
