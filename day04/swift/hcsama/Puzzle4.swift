//
//  Puzzle4.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


class GuardEvent
{
    var day: Int
    var month: Int
    var minute: Int
    var guardId: Int = 0
    var wakes: Bool = false

    init(_ s: String)
    {
        let date = s.components(separatedBy: " ").first!.components(separatedBy: "-")
        month = Int(date[1])!
        day = Int(date[2])!
        minute = Int(s.components(separatedBy: " ")[1][3 ..< 5])!
        switch s.components(separatedBy: " ")[2]
        {
        case "Guard":
            guardId = Int(s.components(separatedBy: " ")[3].substring(fromIndex: 1))!
        case "wakes":
            wakes = true
        case "falls":
            wakes = false
        default:
            exit(-1)
        }
    }
}

class MinuteEntry: Hashable
{
    static func == (lhs: MinuteEntry, rhs: MinuteEntry) -> Bool
    {
        return lhs.guardID == rhs.guardID && lhs.awake == rhs.awake && lhs.day == rhs.day && lhs.month == rhs.month
    }

    var hashValue: Int
    {
        get
        {
            return guardID * 1000 + month * 100 + day
        }
    }

    var guardID: Int
    var awake: Bool
    var day: Int
    var month: Int

    init(_ g: Int, _ a: Bool, _ m: Int, _ d: Int)
    {
        guardID = g
        awake = a
        month = m
        day = d
    }
}

func Puzzle4()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/guards.txt")
    let guardEvents = file.components(separatedBy: "\n").dropLast().sorted().map() { s in return GuardEvent(s) }
    var minutes: [Set<MinuteEntry>] = Array(repeating: [], count: 60)
    var currentGuard: Int = 0
    var lastSleep: Int = 0
    for e in guardEvents
    {
        if e.guardId > 0
        {
            currentGuard = e.guardId
        }
        else if !e.wakes
        {
            lastSleep = e.minute
        }
        else
        {
            for m in lastSleep ..< e.minute
            {
                minutes[m].insert(MinuteEntry(currentGuard, false, e.month, e.day))
            }
        }
    }
    var sleeping = [Int: Int]()
    for m in minutes
    {
        for me in m
        {
            if let curVal = sleeping[me.guardID]
            {
                sleeping[me.guardID] = curVal+1
            }
            else
            {
                sleeping[me.guardID] = 1
            }
        }
    }
    var sleepyGuard = (sleeping.max() { a, b in return a.value < b.value })!.key
    var sleepyMinute = -1
    var maxSleep = -1
    for m in 0 ..< 60
    {
        let curSleep =  (minutes[m].filter() { x in return x.guardID == sleepyGuard }).count
        if curSleep > maxSleep
        {
            maxSleep = curSleep
            sleepyMinute = m
        }
    }
    print("04-01: " + String(sleepyGuard) + " * " + String(sleepyMinute) + " = " + String(sleepyMinute * sleepyGuard))
    sleepyGuard = 0
    sleepyMinute = -1
    maxSleep = -1
    for m in 0 ..< 60
    {
        for g in sleeping.keys
        {
            let curSleep =  (minutes[m].filter() { x in return x.guardID == g }).count
            if curSleep > maxSleep
            {
                maxSleep = curSleep
                sleepyMinute = m
                sleepyGuard = g
            }
        }
    }
    print("04-02: " + String(sleepyGuard) + " * " + String(sleepyMinute) + " = " + String(sleepyMinute * sleepyGuard))
}


