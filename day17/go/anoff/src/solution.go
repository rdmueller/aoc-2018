package main

import (
	"fmt"
	"strings"
	"strconv"
	"math"
)
type coord struct {
	x, y int
}
type ground struct {
	lines []string
}
func (g *ground) print() {
	for _, line := range g.lines {
		fmt.Println(line)
	}
}
func main() {
	input := readInput("../test.txt")
	generateMap(input)
	//g.print()
}

func part1(lines []string) {
	
	fmt.Println("Solution for part1:", 0)
}

func generateMap(readings []string) ground {
	clay := make(map[coord]bool)
	min := coord{math.MaxInt32, 0}
	max := coord{}
	for _, reading := range readings {
		// x=501, y=3..7
		xy := strings.Split(reading, ", ")
		y12 := strings.Split(xy[1][2:], "..")
		x, _ := strconv.Atoi(xy[0][2:])
		y1, _ := strconv.Atoi(y12[0])
		y2, _ := strconv.Atoi(y12[1])
		for y := y1; y <= y2; y++ {
			clay[coord{x,y}] = true
		}
		if x < min.x {
			min.x = x
		} else if x > max.x {
			max.x = x
		}
		if y1 < min.y {
			min.y = y1
		} else if y2 > max.y {
			max.y = y2
		}
	}

	// generate ground map
	var g ground
	for y := min.y; y <= max.y; y++ {
		var l string
		for x := min.x; x <= max.x; x++ {
			if _, hasClay := clay[coord{x, y}]; hasClay {
				l += "#"
			} else {
				l += "."
			}
		}
		g.lines = append(g.lines, l)
	}
	fmt.Println(min, max)
	return g
}