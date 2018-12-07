//
//  Puzzle7.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 07.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


func StepToIndex(_ c: String) -> Int
{
    return Int(c.utf8.first! - "A".utf8.first!)
}

func IndexToStep(_ c: Int) -> String
{
    return String(bytes: ["A".utf8.first! + UInt8(c)], encoding: .utf8)!
}

func StepDuration(_ s: Int, _ timebase: Int) -> Int
{
    return (s+1) + timebase
}

func Puzzle7()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/steps.txt")
    //    let file = "Step C must be finished before step A can begin.\nStep C must be finished before step F can begin.\nStep A must be finished before step B can begin.\nStep A must be finished before step D can begin.\nStep B must be finished before step E can begin.\nStep D must be finished before step E can begin.\nStep F must be finished before step E can begin.\n"
    let steps = file.components(separatedBy: "\n").dropLast()
    var masterTree:[[Int]] = Array(repeating: [] , count: 26)
    var haveSteps = Array(repeating: 1, count: 26)
    for s in steps
    {
        let words = s.components(separatedBy: " ")
        let step = StepToIndex(words[7])
        let predec = StepToIndex(words[1])
        masterTree[step].append(predec)
        haveSteps[step] = 0
        haveSteps[predec] = 0
    }
    var doneSteps = haveSteps
    var stepTree = masterTree
    var resultSequence = ""
    while (doneSteps.filter() { $0 == 0 }).count > 0
    {
        var readySteps:[Int] = []
        for i in 0 ..< 26
        {
            if doneSteps[i] == 0
            {
                if stepTree[i].count == 0
                {
                    readySteps.append(i)
                }
            }
        }
        let doneStep = readySteps.sorted()[0]
        doneSteps[doneStep] = 1
        resultSequence += IndexToStep(doneStep)
        for i in 0 ..< stepTree.count
        {
            if let p = stepTree[i].firstIndex(where: { $0 == doneStep })
            {
                stepTree[i].remove(at: p)
            }
        }
    }
    print("07-01: " + resultSequence)

    let workers = 5
    let timebase = 60
    doneSteps = haveSteps
    stepTree = masterTree
    resultSequence = ""
    var timeIndex = 0
    var workerState: [[Int]] = Array(repeating: [0,0], count: workers)
    while (doneSteps.filter() { $0 == 0 }).count > 0
    {
        var readySteps:[Int] = []
        for i in 0 ..< 26
        {
            if doneSteps[i] == 0
            {
                if stepTree[i].count == 0
                {
                    readySteps.append(i)
                }
            }
        }
        readySteps = readySteps.sorted()
        if readySteps.count > 0
        {
            for i in 0 ..< workers
            {
                if workerState[i][0] == 0
                {
                    workerState[i][0] = timeIndex + StepDuration(readySteps[0], timebase)
                    workerState[i][1] = readySteps[0]
                    doneSteps[readySteps[0]] = -1
                    readySteps.removeFirst()
                }
                if readySteps.count == 0
                {
                    break
                }
            }
        }
        // Next point in time where something gets done
        timeIndex = (workerState.filter({$0[0] > 0}).min(by: {a,b in return a[0] < b[0]}))![0]
        // Now find all the things that will be done now
        var intermediateSequence: String = ""
        for i in 0 ..< workers
        {
            if workerState[i][0] > 0 && workerState[i][0] <= timeIndex
            {
                workerState[i][0] = 0
                let doneStep = workerState[i][1]
                doneSteps[doneStep] = 1
                intermediateSequence += IndexToStep(doneStep)
                for i in 0 ..< stepTree.count
                {
                    if let p = stepTree[i].firstIndex(where: { $0 == doneStep })
                    {
                        stepTree[i].remove(at: p)
                    }
                }
            }
        }
        resultSequence += String(intermediateSequence.sorted())
    }
    print("07-01: " + resultSequence, String(timeIndex))
}
