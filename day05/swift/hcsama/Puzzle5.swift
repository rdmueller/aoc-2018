//
//  Puzzle5.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func CollapsePolymer(_ poly: [Character]) -> [Character]
{
    var p:Int = 0
    var p2:Int = p+1
    var polymer = poly
    while p < polymer.count-1 && p2 < polymer.count
    {
        let c1 = String(polymer[p])
        let c2 = String(polymer[p2])
        if c1.lowercased() == c2.lowercased() && c1 != c2
        {
            polymer[p] = " "
            polymer[p2] = " "
            while p > 0 && polymer[p] == " "
            {
                p -= 1
            }
            while p2 < polymer.count && polymer[p2] == " "
            {
                p2 += 1
            }
        }
        else
        {
            p = p2
            p2 = p+1
        }
    }
    return polymer.filter() {c in return c != " "}
}


func Puzzle5()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/polymer.txt")
    let polymer: [Character] = file.components(separatedBy: "\n").dropLast()[0].filter() { _ in true }
    print("05-01: " + String(CollapsePolymer(polymer).count))
    var minCount = polymer.count
    for c in "abcdefghijklmnopqrstuvwxyz"
    {
        let cnt = CollapsePolymer(polymer.filter() { x in return String(x).lowercased() != String(c) })
        if cnt.count < minCount
        {
            minCount = cnt.count
            print(c)
        }
    }
    print("05-02: " + String(minCount))
}
