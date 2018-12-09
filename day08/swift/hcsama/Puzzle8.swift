//
//  Puzzle8.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 08.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


class Node
{
    var metaData: [Int] = []
    var children: [Node] = []
    var endPos: Int
    static var nodeList: [Node] = []

    init(_ numbers: [Int], _ startPos: Int)
    {
        let numNodes = numbers[startPos]
        let numMeta = numbers[startPos+1]
        endPos = startPos+2
        for _ in 0 ..< numNodes
        {
            let hlp = Node(numbers, endPos)
            children.append(hlp)
            endPos = hlp.endPos
        }
        for _ in 0 ..< numMeta
        {
            metaData.append(numbers[endPos])
            endPos += 1
        }
        Node.nodeList.append(self)
    }

    func Value() -> Int
    {
        var val = 0
        if children.count > 0
        {
            for n in metaData
            {
                if n > 0 && n <= children.count
                {
                    val = val + children[n-1].Value()
                }
            }
        }
        else
        {
            val = metaData.reduce(val, { s, m in s+m})
        }
        return val
    }
}


func Puzzle8()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/license.txt")
    //let file = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2\n"
    let numbers = file.components(separatedBy: "\n").dropLast().first!.components(separatedBy: " ").map() { Int($0)! }
    let nodeTree = Node(numbers, 0)

    print("08-01: " + String(Node.nodeList.reduce(0, {curSum, n in n.metaData.reduce(curSum, { x, m in x + m})})))
    print("08-02: " + String(nodeTree.Value()))
}
