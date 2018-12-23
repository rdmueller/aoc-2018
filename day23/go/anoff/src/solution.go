package main

import (
	"fmt"
)

func main() {
	input := readInput("../input.txt")
	var bots []*Nanobot
	var maxStrength int
	var strongestBot *Nanobot
	for _, line := range input {
		n := input2Nanobot(line)
		bots = append(bots, &n)
		if n.sigStrength > maxStrength {
			maxStrength = n.sigStrength
			strongestBot = &n
		}
	}
	inRange := 0
	for _, n := range bots {
		if strongestBot.InRange(n.pos) {
			inRange++
		}
	}
	fmt.Println("Solution for part1:", inRange, strongestBot)
}