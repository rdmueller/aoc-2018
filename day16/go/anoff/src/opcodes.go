package main

import (
	_"fmt"
)

func getOpCodes() map[string]func(regs [4]int, ops [3]int) [4]int {
	parseOps := func (in [3]int) (int, int, int) {
		A := in[0]
		B := in[1]
		C := in[2]
		if C > 3 {
			panic("Opcode called with target register > 3")
		}
		return A, B, C
	}

	return map[string]func(regs [4]int, ops [3]int) [4]int {
		"addr": func (regs [4]int, ops [3]int) [4]int {
			A, B, C := parseOps(ops)
			regs[C] = regs[A] + regs[B]
			return regs
		},
		"addi": func (regs [4]int, ops [3]int) [4]int {
			A, B, C := parseOps(ops)
			regs[C] = regs[A] + B
			return regs
		},
		"mulr": func (regs [4]int, ops [3]int) [4]int {
			A, B, C := parseOps(ops)
			regs[C] = regs[A] * regs[B]
			return regs
		},
		"muli": func (regs [4]int, ops [3]int) [4]int {
			A, B, C := parseOps(ops)
			regs[C] = regs[A] * B
			return regs
		},
		"banr": func (regs [4]int, ops [3]int) [4]int {
			A, B, C := parseOps(ops)
			regs[C] = regs[A] & regs[B]
			return regs
		},
		"bani": func (regs [4]int, ops [3]int) [4]int {
			A, B, C := parseOps(ops)
			regs[C] = regs[A] & B
			return regs
		},
	}
}