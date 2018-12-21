package main

import (
	_"fmt"
)
// tag::def[]
type operation func(regs [6]int, args [3]int) [6]int


func getOperations() map[string]operation {
	exceedsRegLimit := func (ix int) bool {
		if ix > 5 {
			return true
		}
		return false
	}
	parseArgs := func (in [3]int) (int, int, int) {
		A := in[0]
		B := in[1]
		C := in[2]
		if exceedsRegLimit(C) {
			panic("Opcode called target register out of bounds")
		}
		return A, B, C
	}
	return map[string]operation {
		"addr": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] + regs[B]
			return regs
		},
// end::def[]
		"addi": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] + B
			return regs
		},
		"mulr": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] * regs[B]
			return regs
		},
		"muli": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] * B
			return regs
		},
		"banr": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] & regs[B]
			return regs
		},
		"bani": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] & B
			return regs
		},
		"borr": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) || exceedsRegLimit(B) {
				return regs
			}
			regs[C] = regs[A] | regs[B]
			return regs
		},
		"bori": func(regs [6]int, args [3]int) [6]int {
			A, B, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A] | B
			return regs
		},
		"setr": func(regs [6]int, args [3]int) [6]int {
			A, _, C := parseArgs(args)
			if exceedsRegLimit(A) {
				return regs
			}
			regs[C] = regs[A]
			return regs
		},
		"seti": func(regs [6]int, args [3]int) [6]int {
			A, _, C := parseArgs(args)
			regs[C] = A
			return regs
		},
		"gtir": func(regs [6]int, args [3]int) [6]int {
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
		"gtri": func(regs [6]int, args [3]int) [6]int {
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
		"gtrr": func(regs [6]int, args [3]int) [6]int {
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
		"eqir": func(regs [6]int, args [3]int) [6]int {
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
		"eqri": func(regs [6]int, args [3]int) [6]int {
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
		"eqrr": func(regs [6]int, args [3]int) [6]int {
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