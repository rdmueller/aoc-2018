//
//  Puzzle21.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 21.12.18.
//  Copyright © 2018 Hans-Christian Fuchs. All rights reserved.
//

import Foundation


private enum Opcodes: String, CaseIterable
{
    case addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr
}

private class CPU
{
    var registers: [Int] = [0, 0, 0, 0, 0, 0]
    var ip = 0
    var ipBound = -1
    var ic = 0
    var breakPoint = -1
    var theProg: [Instruction] = []


    struct Instruction
    {
        let op: Opcodes
        let a, b, c: Int
    }

    func Reset()
    {
        registers = [0, 0, 0, 0, 0, 0]
        theProg = []
        ip = 0
        ic = 0
        breakPoint = -1
        ipBound = -1
    }

    func ExecuteOpcode(_ op: Opcodes, _ a: Int, _ b: Int, _ c: Int) -> [Int]
    {
        ic += 1
        var newRegs = registers
        if ipBound >= 0
        {
            newRegs[ipBound] = ip
        }
        switch op
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
        if ipBound >= 0
        {
             ip = newRegs[ipBound]
        }
        ip += 1
        return newRegs
    }

    func ReadProg(_ prog: [String]) -> [Instruction]
    {
        var theProg: [Instruction] = []
        for l in prog
        {
            let hlp = l.components(separatedBy: [" "])
            if hlp[0] == "#ip"
            {
                ipBound = Int(hlp[1])!
            }
            else
            {
                theProg.append(Instruction(op: Opcodes(rawValue: hlp[0])!, a: Int(hlp[1])!, b: Int(hlp[2])!, c: Int(hlp[3])!))
            }
        }
        return theProg
    }

    func ExecuteProgram(_ prog: [String])
    {
        theProg = ReadProg(prog)
        while ip >= 0 && ip < theProg.count && ip != breakPoint
        {
            registers = ExecuteOpcode(theProg[ip].op, theProg[ip].a, theProg[ip].b, theProg[ip].c)
        }
    }

    func ContinueProgram()
    {
        var first = true
        while ip >= 0 && ip < theProg.count && (ip != breakPoint || first)
        {
            registers = ExecuteOpcode(theProg[ip].op, theProg[ip].a, theProg[ip].b, theProg[ip].c)
            first = false
        }
    }
}

private func TheProg()
{
/*
     #ip 5
     seti 123 0 2
     bani 2 456 2
     eqri 2 72 2
     addr 2 5 5
     seti 0 0 5
     seti 0 5 2
     bori 2 65536 4
     seti 6718165 9 2
     bani 4 255 3
     addr 2 3 2
     bani 2 16777215 2
     muli 2 65899 2
     bani 2 16777215 2
     gtir 256 4 3
     addr 3 5 5
     addi 5 1 5
     seti 27 8 5
     seti 0 4 3
     addi 3 1 1
     muli 1 256 1
     gtrr 1 4 1
     addr 1 5 5
     addi 5 1 5
     seti 25 8 5
     addi 3 1 3
     seti 17 3 5
     setr 3 6 4
     seti 7 9 5
     eqrr 2 0 3
     addr 3 5 5
     seti 5 1 5

     00 r2 = 123
     01 r2 = r2 & 0x1c8
     02 r2 = r2 == 0x48
     03 r5 = r2 + r5  // IF r2 == 1 GOTO 05
     04 r5 = 0
.    05 r2 = 0

.    06 r4 = r2 | 0x10000
     07 r2 = 0x6682D5
.    08 r3 = r4 & 0xFF
     09 r2 = r2 + r3
     10 r2 = r2 & 0xFFFFFF
     11 r2 = r2 * 0x1016B
     12 r2 = r2 & 0xFFFFFF
     13 r3 = 0x100 > r4
     14 r5 = r3 + r5  // IF r4 < 256 GOTO 28
     15 GOTO 17
.    17 r3 = 0
.    18 r1 = r3 + 1
     19 r1 = r1 * 0x100
     20 r1 = r1 > r4
     21 r5 = r1 + r5  // IF r4 < (r3+1)*256 GOTO 23   if r3 > r4/256 - 1  => r3 == r4/256
     22 GOTO 24
.    23 GOTO 26
.    24 r3 = r3 + 1
     25 GOTO 18
.    26 r4 = r3
     27 GOTO 08
.    28 r3 = r2 == r0
     29 r5 = r3 + r5  // IF r2 == r0 END
     30 GOTO 06

*/
}

func Puzzle21()
{
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/prog21.txt")
    let lines: [String] = file.components(separatedBy: "\n").dropLast().filter {_ in true}
    let cpu = CPU()
    cpu.breakPoint = 28
    cpu.ExecuteProgram(lines)
    let targetVal = cpu.registers[2]
    cpu.Reset()
    cpu.registers[0] = targetVal
    cpu.ExecuteProgram(lines)
    print("21-01:", cpu.ic, cpu.registers[0])
    var found = false
    var r2 = 0x6682D5
    var r4 = 0x10000
    var numbers: [Int] = []
    while !found
    {
        r2 = r2 + (r4 & 0xFF)
        r2 = r2 & 0xFFFFFF
        r2 = r2 * 0x1016B
        r2 = r2 & 0xFFFFFF
        if r4 < 256
        {
            if !numbers.contains(r2)
            {
                numbers.append(r2)
                r4 = r2 | 0x10000
                r2 = 0x6682D5
            }
            else
            {
                found = true
            }
        }
        else
        {
            r4 = r4 / 256
        }
    }
    print("21-02:", numbers.last!)
}
