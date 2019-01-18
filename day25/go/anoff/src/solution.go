package main

import (
	"fmt"
	"strings"
	_"math"
)

func main() {
	input := readInput("../input.txt")
	points := parseInput(input)
	part1(points)
}

func part1(points []Position4) {
	var constellations []Constellation
	for _, p := range points {
		foundConstellation := false
		for ix, _ := range constellations {
			if p.isPart(constellations[ix]) {
				// fmt.Println("add to existing constellation")
				constellations[ix] = append(constellations[ix], p)
				foundConstellation = true
				break
			}
		}
		if !foundConstellation {
			// fmt.Println("new constellation created")
			constellations = append(constellations, Constellation{p})
		}
	}
	// merge constellations if possible
	for {
		startLength := len(constellations)
		for i := 0; i < len(constellations); i++ {
			for o := 0; o < len(constellations); o++ {
				if i != o && constellations[i].intersects(constellations[o]) {
					constellations[i] = append(constellations[i], constellations[o]...)
					constellations = append(constellations[:o], constellations[o+1:]...)
					fmt.Println("merged constellations..")
				}
			}
		}
		if len(constellations) == startLength {
			break
		}
	}
	fmt.Println("Solution for part1:", len(constellations))
}

func parseInput(input []string) []Position4 {
	var points []Position4
	for _, line := range input {
		strNums := strings.Split(line, ",")
		nums := StringSlice2IntSlice(strNums)
		var p = Position4{nums[0], nums[1], nums[2], nums[3]}
		points = append(points, p)
	}
	return points
}

/*
620, too high
*/