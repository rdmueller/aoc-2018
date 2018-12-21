package main

import (
	"fmt"
)

var DEBUG = true

func main() {
	input := readInput("../input.txt")
	p := NewProgram(input)
	p.print()
	part1(&p)
	//part2(&p)
}

func part1(p *program) {
	
}

func part2(p *program) {
	E := 10550400 + 875
	A := 0
	for B := 1; B <= E; B++ {
		if E % B == 0 {
			A += B
		}
	}
	fmt.Println("Solution for part 2:", A)
}
