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