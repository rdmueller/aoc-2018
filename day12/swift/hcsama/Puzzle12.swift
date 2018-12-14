//
//  Puzzle12.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 12.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


var rules: [[String]] = []

func NewState(_ s: String, _ zero: inout Int) -> String
{
    var state = s
    while state.substring(toIndex: 4) != "...."
    {
        zero += 1
        state.insert(".", at: state.startIndex)
    }
    while state.substring(fromIndex: state.length-4) != "...."
    {
        state.append(".")
    }
    var newState = ""
    for i in 2..<state.length-2
    {
        for r in rules
        {
            if state.substring(r: i-2 ..< i-2+5) == r[0]
            {
                newState.append(r[2])
                break
            }
        }
    }
    zero -= 2
    return newState
}

func StateValue(_ state: String, _ zero: Int) -> Int
{
    var value = 0
    for i in 0..<state.length
    {
        value += state[i] == "." ? 0 : i - zero
    }
    return value
}

struct StoredState
{
    var zero: Int
    var state: String
    {
        get { return _state }
        set { _state = newValue.trimmingCharacters(in: ["."]) }
    }
    private var _state: String = ""

    init(_ s: String, _ z: Int)
    {
        let offset = s.distance(from: s.startIndex, to: s.firstIndex(of: "#")!)
        self.zero = z - offset
        self.state = s
    }
}

func Puzzle12()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/plants.txt")
    let lines = file.components(separatedBy: "\n").dropLast()
    rules = lines.dropFirst(2).map() { $0.components(separatedBy: " ")}
    var states: [StoredState] = []
    var state = lines[0].components(separatedBy: " ").last!
    var zero = 0
    // Leave out initial state on purpose
    // states.append(StoredState(state, zero))
    for _ in 0..<20
    {
        state = NewState(state, &zero)
        states.append(StoredState(state, zero))
    }
    print("12-01:", StateValue(state, zero))
    // Ok, 50 Billion times - NOT
    let fiftyB = 50000000000
    for i in 20..<fiftyB
    {
        state = NewState(state, &zero)
        let newState = StoredState(state, zero)
        if let found = states.firstIndex(where: { s in s.state == newState.state })
        {
            print("Repeater found:", i, found)
            let delta = newState.zero - states[found].zero
            let zeroFiftyB = delta * (fiftyB-(i+1)) + newState.zero
            print("12-02:", StateValue(newState.state, zeroFiftyB))
            break
        }
        states.append(newState)
    }
}
