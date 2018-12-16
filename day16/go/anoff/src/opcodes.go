package main

import (
	_"fmt"
)

type operation func(regs [4]int, args [3]int) [4]int


func getOperations() map[string]operation {
	parseArgs := func (in [3]int) (int, int, int) {
		A := in[0]
		B := in[1]
		C := in[2]
		if C > 3 {
			panic("Opcode called with target register > 3")
		}
		return A, B, C
	}
	exceedsRegLimit := func (ix int) bool {
		if ix > 3 {
			return true
		}
		return false
	}
	return map[string]operation {
		"addr": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] + regs[B]
			return regs
		},
		"addi": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] + B
			return regs
		},
		"mulr": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] * regs[B]
			return regs
		},
		"muli": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] * B
			return regs
		},
		"banr": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] & regs[B]
			return regs
		},
		"bani": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] & B
			return regs
		},
		"borr": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] | regs[B]
			return regs
		},
		"bori": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] | B
			return regs
		},
		"setr": func (regs [4]int, args [3]int) [4]int {
			A, _, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A]
			return regs
		},
		"seti": func (regs [4]int, args [3]int) [4]int {
			A, _, C := parseArgs(args)
			regs[C] = A
			return regs
		},
		"gtir": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(B) {
				return regs
			}
			if A > regs[B] {
				regs[C] = 1
			} else {
				regs[C] = 0
			}
			return regs
		},
		"gtri": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			if regs[A] > B {
				regs[C] = 1
			} else {
				regs[C] = 0
			}
			return regs
		},
		"gtrr": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			if regs[A] > regs[B] {
				regs[C] = 1
			} else {
				regs[C] = 0
			}
			return regs
		},
		"eqir": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(B) {
				return regs
			}
			if A == regs[B] {
				regs[C] = 1
			} else {
				regs[C] = 0
			}
			return regs
		},
		"eqri": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			if regs[A] == B {
				regs[C] = 1
			} else {
				regs[C] = 0
			}
			return regs
		},
		"eqrr": func (regs [4]int, args [3]int) [4]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			if regs[A] == regs[B] {
				regs[C] = 1
			} else {
				regs[C] = 0
			}
			return regs
		},
	}
}