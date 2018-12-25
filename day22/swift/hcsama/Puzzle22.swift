//
//  Puzzle22.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 22.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func Puzzle22()
{
    //let caveDepth = 510 ; let targetPos = Coord(x: 10, y: 10)
    let caveDepth = 9171 ; let targetPos = Coord(x: 7, y: 721)
    var grid: [[Character]] = []
    var erosion: [[Int64]] = []
    enum ToolType: CaseIterable {case neither, torch, climbing }
    let tooling: [Character:Set<ToolType>] = [".":[.torch, .climbing], "=":[.climbing, .neither], "|":[.torch, .neither]]
    let rType: [Character] = [".", "=", "|"]
    let infiniDist = (targetPos.x+targetPos.y)*8+1


    func CalcGridPos(_ x: Int, _ y: Int)
    {
        var geo: Int64 = 0

        if (x != 0 || y != 0) && !(x == targetPos.x && y == targetPos.y)
        {
            if y == 0
            {
                geo = (Int64(x) * 16807) % 20183
            }
            else if x == 0
            {
                geo = (Int64(y) * 48271) % 20183
            }
            else
            {
                geo = erosion[y][x-1] * erosion[y-1][x]
            }
        }
        erosion[y][x] = (geo + Int64(caveDepth)) % 20183
        grid[y][x] = rType[Int(erosion[y][x]%3)]
    }

    func CalcGrid(_ xSize: Int, _ ySize: Int)
    {
        grid = Array(repeating: Array(repeating: " ", count: xSize), count: ySize)
        erosion = Array(repeating: Array(repeating: 0, count: xSize), count: ySize)
        for y in 0..<grid.count
        {
            for x in 0..<grid[0].count
            {
                CalcGridPos(x, y)
            }
        }
    }

    var surround = [Coord(x: 0, y: 1), Coord(x: 0, y: -1), Coord(x: -1, y: 0), Coord(x: 1, y: 0)]

    func CalcDistN() -> Int
    {
        struct Visit: Hashable {var pos: Coord; var tool: ToolType}
        struct Stack {var dist: Int; var visit: Visit}
        var stack: [Stack] = [Stack(dist: 0, visit: Visit(pos: Coord(x: 0, y: 0), tool: .torch))]
        var visited: Set<Visit> = []
        while true
        {
            stack.sort(by: {lhs, rhs in
                if(lhs.dist > rhs.dist)
                {
                    return true
                }
                if(lhs.dist == rhs.dist)
                {
                    if(lhs.visit.pos.x > rhs.visit.pos.x)
                    {
                        return true
                    }
                    if(lhs.visit.pos.x == rhs.visit.pos.x)
                    {
                        if(lhs.visit.pos.y > rhs.visit.pos.y)
                        {
                            return true
                        }
                    }
                }
                return false
                })
            let s = stack.removeLast()
            if s.visit.pos.x == targetPos.x && s.visit.pos.y == targetPos.y
            {
                print(s.dist, s.visit.pos.x, s.visit.pos.y, s.visit.tool)
            }
            if !visited.contains(s.visit)
            {
                visited.insert(s.visit)
                if s.visit.pos.x == targetPos.x && s.visit.pos.y == targetPos.y && s.visit.tool == .torch
                {
                    return s.dist
                }
                for t in ToolType.allCases
                {
                    if t != s.visit.tool && tooling[grid[s.visit.pos.y][s.visit.pos.x]]!.contains(t)
                    {
                        let s2 = Stack(dist: s.dist+7, visit: Visit(pos: Coord(x: s.visit.pos.x, y: s.visit.pos.y), tool: t))
                        stack.append(s2)
                    }
                }
                for c in surround
                {
                    let newPos = s.visit.pos + c
                    if newPos.x >= 0 && newPos.y >= 0 && newPos.x < grid[0].count && newPos.y < grid.count
                    {
                        if tooling[grid[newPos.y][newPos.x]]!.contains(s.visit.tool)
                        {
                            let s2 = Stack(dist: s.dist+1, visit: Visit(pos: newPos, tool: s.visit.tool))
                            stack.append(s2)
                        }
                    }
                }
            }
        }
    }

    CalcGrid(targetPos.x+1, targetPos.y+1)
    print("22-01:", grid.reduce(0, {$0 + $1.reduce(0, {$0 + rType.firstIndex(of: $1)!})}))
    CalcGrid(targetPos.x*10+1, targetPos.y*10+1)
    print("22-02:", CalcDistN())
}
