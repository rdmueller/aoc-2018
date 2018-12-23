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
	stepSize := minStrength(bots)
	fmt.Println("X dim", xmax-xmin, (xmax-xmin)/stepSize)
	fmt.Println("Y dim", ymax-ymin, (ymax-ymin)/stepSize)
	fmt.Println("Z dim", zmax-zmin, (zmax-zmin)/stepSize)
	fmt.Println("Room size", (xmax-xmin)/stepSize * (ymax-ymin)/stepSize * (zmax-zmin)/stepSize)
	
	// helper function to get score any point
	botsInRange := func (p Position3) int {
		count := 0
		for _, n := range bots {
			if n.InRange(p) {
				count++
			}
		}
		return count
	}

	p1 := Position3{xmin, ymin, zmin}
	p2 := Position3{xmax, ymax, zmax}
	var mostBots, mostBotsPrev int
	var mostBotsPos Position3
	for stepSize := minStrength(bots); stepSize > 0; stepSize /= 10 {
		mostBots, mostBotsPos, _ = searchGrid(p1, p2, stepSize, botsInRange)
		fmt.Println("..best score with stepsize", stepSize, "was", mostBots)
		if mostBots < mostBotsPrev {
			panic("Found lower best score than before :o")
		}
		mostBotsPrev = mostBots
		// make sure grid length 1 is hit
		if stepSize < 10 && stepSize != 1 {
			stepSize = 10
		}
		p1 = Position3{mostBotsPos.x - stepSize/2, mostBotsPos.y - stepSize/2, mostBotsPos.z - stepSize/2}
		p2 = Position3{mostBotsPos.x + stepSize/2, mostBotsPos.y + stepSize/2, mostBotsPos.z + stepSize/2}
	}
	bestPos := mostBotsPos
	shortestDistance := bestPos.Manhattan(Position3{0,0,0})
	fmt.Println("Solution for part2:", shortestDistance, "at", bestPos, "most bots:", mostBots)
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

// -> highest score, position closest to origin with highest score, all positions with highest score
func searchGrid(p1 Position3, p2 Position3, stepSize int, scoreFn func (Position3) int) (int, Position3, []Position3) {
	bestScore := 0
	bestScoreOccurence := 0
	var bestScorePos []Position3
	for z := p1.z; z < p2.z+stepSize; z += stepSize { // offset max by +stepSize to make sure it always covers p2
		for y := p1.y; y < p2.y+stepSize; y += stepSize {
			for x := p1.x; x < p2.x+stepSize; x += stepSize {
				p := Position3{x,y,z}
				score := scoreFn(p)
				if score > bestScore {
					bestScore = score
					bestScoreOccurence = 1
					bestScorePos = []Position3{p}
				} else if score == bestScore {
					bestScorePos = append(bestScorePos, p)
				}
			}
		}	
	}
	var returnPos Position3
	// check for multiple occurences
	if bestScoreOccurence > 1 {
		// panic("Handle multiple best positions")
		shortestDistance := math.MaxInt32
		for _, p := range bestScorePos {
			d := p.Manhattan(Position3{0,0,0})
			if d < shortestDistance {
				shortestDistance = d
				returnPos = p
			}
		}
	} else {
		returnPos = bestScorePos[0]
	}
	return bestScore, returnPos, bestScorePos
}
