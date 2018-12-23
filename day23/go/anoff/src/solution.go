package main

import (
	"fmt"
	"math"
)

func main() {
	input := readInput("../input.txt")
	var bots []*Nanobot
	for _, line := range input {
		n := input2Nanobot(line)
		bots = append(bots, &n)
	}
	part1(bots)
	part2(bots)
}

func part1(bots []*Nanobot) {
	var maxStrength int
	var strongestBot *Nanobot
	for _, n := range bots {
		if n.sigStrength > maxStrength {
			maxStrength = n.sigStrength
			strongestBot = n
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

func part2(bots []*Nanobot) {
	//var world map[Position3]int // count of nanobots in range for each pos
	minStrength := math.MaxInt32
	for _, n := range bots {
		if n.sigStrength < minStrength {
			minStrength = n.sigStrength
		}
	}
	fmt.Println("Solution for part2:", minStrength)
}

/*


	*/