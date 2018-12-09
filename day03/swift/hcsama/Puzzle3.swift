//
//  Puzzle3.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


class Claim
{
    var id: Int32
    var x: Int
    var y: Int
    var width: Int
    var height: Int
    var fabricLocations: Set<String> = []

    init(_ s: String)
    {
        id = Int32(s.components(separatedBy: "@").first!.substring(fromIndex: 1).trimmingCharacters(in: [" "]))!
        let xy = s.components(separatedBy: "@")[1].components(separatedBy: ":").first!.components(separatedBy: ",")
        x = Int(xy.first!.trimmingCharacters(in: [" "]))!
        y = Int(xy.last!.trimmingCharacters(in: [" "]))!
        let wh = s.components(separatedBy: "@")[1].components(separatedBy: ":").last!.components(separatedBy: "x")
        width = Int(wh.first!.trimmingCharacters(in: [" "]))!
        height = Int(wh.last!.trimmingCharacters(in: [" "]))!
        for i in 0 ..< width
        {
            for j in 0 ..< height
            {
                fabricLocations.insert(String(x+i) + "x" + String(y+j))
            }
        }
    }
}


func Puzzle3()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/claims.txt")
    let claims = file.components(separatedBy: "\n").dropLast().map() {s in return Claim(s)}
    var overlaps: Set<String> = []
    var hits: [Bool] = Array(repeating: false, count: claims.count)
    for i in 0 ..< claims.count
    {
        var myOverlaps: Set<String> = []
        for j in i+1 ..< claims.count
        {
            let hit = claims[i].fabricLocations.intersection(claims[j].fabricLocations)
            myOverlaps = myOverlaps.union(hit)
            if hit.count > 0
            {
                hits[i] = true
                hits[j] = true
            }
        }
        overlaps = overlaps.union(myOverlaps)
    }
    print("03-1: " + String(overlaps.count))

    for i in 0 ..< hits.count
    {
        if !hits[i]
        {
            print("03-2: " + String(claims[i].id))
            break;
        }
    }
}

