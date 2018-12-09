//
//  Puzzle2.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func Puzzle2()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/boxes.txt")
    let boxes = file.components(separatedBy: "\n").dropLast()
    //    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/boxes_anoff.txt")
    //    let boxes = ["abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab"]
    var countTwo: Int = 0
    var countThree: Int = 0
    for s in boxes
    {
        //print("-" + s)
        let hlp = String(s.sorted())
        for c in hlp
        {
            if hlp.contains(String(c)+String(c)+String(c)) && !hlp.contains(String(c)+String(c)+String(c)+String(c))
            {
                //print(hlp + " contains three " + String(c))
                countThree += 1
                break
            }
        }
        for c in hlp
        {
            if hlp.contains(String(c)+String(c)) && !hlp.contains(String(c)+String(c)+String(c))
            {
                //print(hlp + " contains two " + String(c))
                countTwo += 1
                break
            }
        }
    }
    //print("lines countTwo countThree", boxes.count, countTwo, countThree)
    print("02-1: " + String(countTwo * countThree))

    let sortedBoxes = boxes.sorted()
    let rightBoxes = sortedBoxes.compactMap()
    { s1 -> String? in
        let b = sortedBoxes.compactMap()
        { s2 -> String? in
            if s1 != s2
            {
                var p:Int = 0
                while(s1.substring(toIndex: p) == s2.substring(toIndex: p))
                {
                    p += 1
                }
                if(s1.substring(fromIndex: p) == s2.substring(fromIndex: p))
                {
                    return s2.substring(toIndex: p-1) + s2.substring(fromIndex: p)
                }
            }
            return nil
        }
        return b.count > 0 ? b[0] : nil
    }
    print("02-2: " + rightBoxes.first!)
}
