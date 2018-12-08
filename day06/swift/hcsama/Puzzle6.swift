//
//  Puzzle6.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func TotalDist(_ coordinates:[[Int]], _ x: Int, _ y: Int) -> Int
{
    var totalDist = 0
    for c in coordinates
    {
        totalDist += abs(c[0]-x) + abs(c[1]-y)
    }
    return totalDist
}

func Puzzle6()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/coordinates.txt")
    //    let file = "1, 1\n1, 6\n8, 3\n3, 4\n5, 5\n8, 9\n"

    var coordinates = file.components(separatedBy: "\n").dropLast().map()
    { s in
        return s.components(separatedBy: ",").map() { c in return Int(c.trimmingCharacters(in: [" "]))! }
    }
    var minX = coordinates[0][0]
    var minY = coordinates[0][1]
    var maxX = minX
    var maxY = minY
    for c in coordinates
    {
        if c[0] < minX
        {
            minX = c[0]
        }
        if c[0] > maxX
        {
            maxX = c[0]
        }
        if c[1] < minY
        {
            minY = c[1]
        }
        if c[1] > maxY
        {
            maxY = c[1]
        }
    }
    let sizeX = maxX-minX+1+2
    let sizeY = maxY-minY+1+2
    let nCoord = coordinates.count
    coordinates = coordinates.map() { c in return [c[0]+1-minX, c[1]+1-minY] }
    var grid = Array(repeating: Array(repeating: Array(repeating: 0, count: nCoord+1), count: sizeX), count: sizeY)
    for y in 0 ..< sizeY
    {
        for x in 0 ..< sizeX
        {
            var minDist = sizeX+sizeY
            for c in 0 ..< nCoord
            {
                let hlp = abs((coordinates[c][0] - x)) + abs((coordinates[c][1] - y))
                if hlp < minDist
                {
                    minDist = hlp
                    grid[y][x][nCoord] = c
                }
                grid[y][x][c] = hlp
            }
            if (grid[y][x].dropLast().filter() { i in return i == minDist }).count > 1
            {
                grid[y][x][nCoord] = -1
            }
        }
    }

    var sizes = Array(repeating: 0, count: nCoord)
    for y in 0 ..< sizeY
    {
        for x in 0 ..< sizeX
        {
            if grid[y][x][nCoord] >= 0 && sizes[grid[y][x][nCoord]] >= 0
            {
                if x == 0 || y == 0 || x == sizeX-1 || y == sizeY-1
                {
                    sizes[grid[y][x][nCoord]] = -1
                }
                else
                {
                    sizes[grid[y][x][nCoord]] += 1
                }
            }
        }
    }
    print("06-01: " + String((sizes.filter(){ s in return s >= 0 }).max()!))

    let maxDist = 10000
    var safeArea = 0

    let schwerPunkt = coordinates.reduce(into: Array(repeating: 0, count: 2), { result, c in result[0] += c[0] ; result[1] += c[1] }).map() { s in return s/nCoord }

    for x in schwerPunkt[0]-maxDist/nCoord ... schwerPunkt[0]+maxDist/nCoord
    {
        for y in schwerPunkt[1]-maxDist/nCoord ... schwerPunkt[1]+maxDist/nCoord
        {
            if TotalDist(coordinates, x, y) < maxDist
            {
                safeArea += 1
            }
        }
    }
    print("06-02: " + String(safeArea))
}

