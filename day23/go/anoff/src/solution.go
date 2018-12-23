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
	stepSize := minStrength(bots)/3
	fmt.Println("X dim", xmax-xmin, (xmax-xmin)/stepSize)
	fmt.Println("Y dim", ymax-ymin, (ymax-ymin)/stepSize)
	fmt.Println("Z dim", zmax-zmin, (zmax-zmin)/stepSize)
	fmt.Println("Room size", (xmax-xmin)/stepSize * (ymax-ymin)/stepSize * (zmax-zmin)/stepSize)
	
	// create a hashmap of positions
	world := make(map[Position3]int) // count of nanobots in range for each pos
	mostBots := 0
	mostBotsCount := 1
	var mostBotsPos Position3
	for z := zmin; z <= zmax; z += stepSize {
		for y := ymin; y <= ymax; y += stepSize {
			for x := xmin; x <= xmax; x += stepSize {
				p := Position3{x,y,z}
				botsInRange := 0
				for _, n := range bots {
					if n.InRange(p) {
						botsInRange++
					}
				}
				if botsInRange > mostBots {
					mostBots = botsInRange
					mostBotsCount = 1
					mostBotsPos = p
				} else if botsInRange == mostBots {
					mostBotsCount++
				}
				world[p] = botsInRange
			}
		}	
	}
	// check for multiple occurences
	if mostBotsCount > 1 {
		panic("Handle multiple best positions")
	}
	// find closest point to origin
	bestPos := mostBotsPos
	i := 0
	for {
		switch i%3 {
			case 0:
				bestPos.x--
			case 1:
				bestPos.y--
			case 2:
				bestPos.z--
		}
		botsInRange := 0
		for _, n := range bots {
			if n.InRange(bestPos) {
				botsInRange++
			}
		}
		if botsInRange < mostBots {
			break
		} else {
		//	fmt.Println("approaching")
		}
		i++
	}
	shortestDistance := bestPos.Manhattan(Position3{0,0,0}) + 1
	fmt.Println("Solution for part2:", shortestDistance, "at", bestPos)
}

func minStrength(bots []*Nanobot) int {
	minStrength := math.MaxInt32
	for _, n := range bots {
		if n.sigStrength < minStrength {
			minStrength = n.sigStrength
		}
	}
	return minStrength
}

/*
answers
127664974, too high
104668669, too low
*/