//
//  Puzzle19.swift
//  AdventOfCode2018
//
//  Created by Hans-Christian Fuchs on 19.12.18.
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

    struct Instruction
    {
        let op: Opcodes
        let a, b, c: Int
    }

    func Reset()
    {
        registers = [0, 0, 0, 0, 0, 0]
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
        let theProg = ReadProg(prog)
        while ip >= 0 && ip < theProg.count && ip != breakPoint
        {
            registers = ExecuteOpcode(theProg[ip].op, theProg[ip].a, theProg[ip].b, theProg[ip].c)
        }
    }
}

func TheProg()
{
/*
     addi 3 16 3
     seti 1 8 1
     seti 1 3 4
     mulr 1 4 2
     eqrr 2 5 2
     addr 2 3 3
     addi 3 1 3
     addr 1 0 0
     addi 4 1 4
     gtrr 4 5 2
     addr 3 2 3
     seti 2 6 3
     addi 1 1 1
     gtrr 1 5 2
     addr 2 3 3
     seti 1 5 3
     mulr 3 3 3
     addi 5 2 5
     mulr 5 5 5
     mulr 3 5 5
     muli 5 11 5
     addi 2 5 2
     mulr 2 3 2
     addi 2 21 2
     addr 5 2 5
     addr 3 0 3
     seti 0 4 3
     setr 3 1 2
     mulr 2 3 2
     addr 3 2 2
     mulr 3 2 2
     muli 2 14 2
     mulr 2 3 2
     addr 5 2 5
     seti 0 3 0

    00 goto 17
    01 r1 = 1
    02 r4 = 1
    03 r2 = r4*r1
    04 r2 = r2 == r5
    05 if r2 == 1 goto 07
    06 goto 08
    07 r0 += r1
    08 r4 += 1
    09 r2 = r4 > r5
    10 if r2 == 1 goto 12
    11 goto 03
    12 r1 += 1
    13 r2 = r1 > r5
    14 if r2 == 1 goto 16
    15 goto 02
    16 END
*/
}

func Puzzle19()
{
    //let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/prog_test.txt")
    let file = try! String(contentsOfFile: "/Users/hch/Documents/XCode/AdventOfCode2018/AdventOfCode2018/prog.txt")
    let lines: [String] = file.components(separatedBy: "\n").dropLast().filter {_ in true}
    let cpu = CPU()
    cpu.ExecuteProgram(lines)
    print("19-01:", cpu.ic, cpu.registers[0])
    cpu.Reset()
    cpu.registers[0] = 1
    cpu.breakPoint = 1 // run only to where multipliers are determined
    cpu.ExecuteProgram(lines)
    var summe = 0
    for i in 1...cpu.registers[5]
    {
        if cpu.registers[5]%i == 0
        {
            summe += i
        }
    }
    print("19-02:", summe)
}
