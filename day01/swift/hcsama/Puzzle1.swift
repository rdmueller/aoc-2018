//
//  Puzzle1.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func Puzzle1()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/freq.txt")
    let deltas = file.components(separatedBy: "\n").dropLast();

    print("01-1: " + String(deltas.reduce(into: Int32(0)) { (finalFreq, delta) in finalFreq += Int32(delta)! }))
    var notFound: Bool = true
    var freq: Set<Int32> = []
    var curFreq: Int32 = 0
    while(notFound)
    {
        for d in deltas
        {
            notFound = !freq.contains(curFreq)
            if(notFound)
            {
                freq.insert(curFreq)
                curFreq += Int32(d)!
            }
        }
    }
    print("01-2: " + String(curFreq))
}

