package main

import (
	"fmt"
)

var DEBUG = true

func main() {
	input := readInput("../input.txt")
	p := NewProgram(input)
	p.print()
	//part1(&p)
	p = NewProgram(input)
	part2(&p)
}

func part1(p *program) {
	for {
		programEnded := p.step(DEBUG)
		if programEnded {
			fmt.Println("Solution for part 1:", p.registers[0])
			break
		}
	}
}

func part2(p *program) {
	p.registers[0] = 1
	for i := 0; i < 20; i++ {
		programEnded := p.step(DEBUG)
		if programEnded {
			fmt.Println("Solution for part 2:", p.registers[0])
			break
		}
	}
}
