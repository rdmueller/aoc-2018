package main

import (
	"fmt"
)

var DEBUG = false

func main() {
	input := readInput("../input.txt")
	p := NewProgram(input)
	p.print()
	part1(&p)
	p = NewProgram(input)
	part2(&p)
}

func part1(p *program) {
	for {
		programEnded := p.step(DEBUG)
		if p.registers[p.pointerRegister] == 28 {
			fmt.Println("Solution for part 1:", p.registers[3])
			break
		}
		if programEnded {
			break
		}
	}
}

func part2(p *program) {
	seen := make(map[int]int)
	prev := 0
	i := 0
	for {
		programEnded := p.step(DEBUG)
		if p.registers[p.pointerRegister] == 28 {
			d := p.registers[3]
			if _, has := seen[d]; has {
				fmt.Println("Solution for part 2:", prev, ", (after seeing", d, "twice)")
				break
			}
			if len(seen) % 500 == 0 {
				fmt.Println("seen", len(seen), "results")
			}
			seen[d] = i
			prev = d
		}
		if programEnded {
			break
		}
		i++
	}
}
