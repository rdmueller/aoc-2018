//
//  Puzzle16.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 16.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


enum Opcodes: String, CaseIterable
{
    case addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr
}

class CPU
{
    var registers: [Int] = [0, 0, 0, 0]

    func Reset()
    {
        registers = [0, 0, 0, 0]
    }

    func ExecuteOpcode(_ op: String, _ a: Int, _ b: Int, _ c: Int) -> [Int]
    {
        var newRegs = registers
        switch Opcodes(rawValue: op)!
        {
        case .addr:
            newRegs[c] = newRegs[a] + newRegs[b]
        case .addi:
            newRegs[c] = newRegs[a] + b
        case .mulr:
            newRegs[c] = newRegs[a] * newRegs[b]
        case .muli:
            newRegs[c] = newRegs[a] * b
        case .banr:
            newRegs[c] = newRegs[a] & newRegs[b]
        case .bani:
            newRegs[c] = newRegs[a] & b
        case .borr:
            newRegs[c] = newRegs[a] | newRegs[b]
        case .bori:
            newRegs[c] = newRegs[a] | b
        case .setr:
            newRegs[c] = newRegs[a]
        case .seti:
            newRegs[c] = a
        case .gtir:
            newRegs[c] = a > newRegs[b] ? 1 : 0
        case .gtri:
            newRegs[c] = newRegs[a] > b ? 1 : 0
        case .gtrr:
            newRegs[c] = newRegs[a] > newRegs[b] ? 1 : 0
        case .eqir:
            newRegs[c] = a == newRegs[b] ? 1 : 0
        case .eqri:
            newRegs[c] = newRegs[a] == b ? 1 : 0
        case .eqrr:
            newRegs[c] = newRegs[a] == newRegs[b] ? 1 : 0
        }
        return newRegs
    }
}

func Puzzle16()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/opcodes.txt")
    var lines = file.components(separatedBy: "\n").dropLast()
    let cpu: CPU = CPU()
    var computedRegisters: [Opcodes:[Int]] = [:]
    var relevantSamples = 0
    var matrix: [[Opcodes]] = Array(repeating: [], count: Opcodes.allCases.count)
    var identifiedOpcodes: [Int:Opcodes] = [:]
    var startOfProg: Int = 0
    for y in 0 ..< lines.count-1
    {
        if lines[y].count == 0
        {
            if  lines[y+1].count == 0
            {
                startOfProg = y+2
                break
            }
            else
            {
                continue
            }
        }
        switch lines[y][0]
        {
        case "B":
            cpu.registers = lines[y].components(separatedBy: ["[", ",", "]"]).map({$0.trimmingCharacters(in: [" "])}).dropFirst().dropLast().map({Int($0)!})
            computedRegisters = [:]
        case "A":
            let newRegisters = lines[y].components(separatedBy: ["[", ",", "]"]).map({$0.trimmingCharacters(in: [" "])}).dropFirst().dropLast().map({Int($0)!})
            let matchingRegisters = computedRegisters.filter({key, value in value.dropLast().elementsEqual(newRegisters)})
            let cnt:Int = matchingRegisters.count
            if cnt > 2
            {
                relevantSamples += 1
            }
            for m in matchingRegisters
            {
                if !matrix[m.value[4]].contains(m.key)
                {
                    matrix[m.value[4]].append(m.key)
                }
            }
        default:
            var hlp = lines[y].components(separatedBy: [" "]).map({Int($0)!})
            for op in Opcodes.allCases
            {
                var e = cpu.ExecuteOpcode(op.rawValue, hlp[1], hlp[2], hlp[3])
                e.append(hlp[0])
                computedRegisters[op] = e
            }
        }
    }
    while matrix.reduce(0, {$0+$1.count}) > 0
    {
        for y in 0 ..< matrix.count
        {
            if matrix[y].count == 1
            {
                let op = matrix[y][0]
                identifiedOpcodes[y] = op
                for i in 0 ..< matrix.count
                {
                    if let j = matrix[i].firstIndex(of: op)
                    {
                        matrix[i].remove(at: j)
                    }
                }
            }
        }
    }
    cpu.Reset()
    for line in lines.dropFirst(startOfProg+1)
    {
        if line.count > 0
        {
            let hlp = line.components(separatedBy: [" "]).map({Int($0)!})
            cpu.registers = cpu.ExecuteOpcode(identifiedOpcodes[hlp[0]]!.rawValue, hlp[1], hlp[2], hlp[3])
        }
    }

    print("16-01:", relevantSamples)
    print("16-02:", cpu.registers[0])
}
