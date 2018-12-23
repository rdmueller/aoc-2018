package main

import (
	"fmt"
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
	var xmin,xmax,ymin,ymax,zmin,zmax int
	for _, n := range bots {
		if n.pos.x < xmin {
			xmin = n.pos.x
		}
		if n.pos.x > xmax {
			xmax = n.pos.x
		}
		if n.pos.y < ymin {
			ymin = n.pos.y
		}
		if n.pos.y > ymax {
			ymax = n.pos.y
		}
		if n.pos.z < zmin {
			zmin = n.pos.z
		}
		if n.pos.z > zmax {
			zmax = n.pos.z
		}
	}
	i := 0
	for z := zmin; z <= zmax; z += 10000000 {
		for y := ymin; y <= ymax; y += 10000000 {
			for x := xmin; x <= xmax; x += 10000000 {
				i++
			}
		}
	}
	fmt.Println("Solution for part2:", i)
}
