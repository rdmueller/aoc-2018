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
	lines [][]string
}
func (g *ground) print() {
	for _, line := range g.lines {
		fmt.Println(strings.Join(line, ""))
	}
}
// check if point is a confined area (left&right walls, bottom floored)
func (g *ground) isConfined(p coord) bool {
	y := p.y
	// last line is never confined
	if y == len(g.lines) - 1 {
		return false
	}
	for left := p.x; left >= 0; left-- {
		if g.lines[y+1][left] != "#" && g.lines[y+1][left] != "o" {
			return false
		}
		if g.lines[y][left] == "#" {
			break
		}
		if left == 0 {
			return false
		}
	}
	for right := p.x; right < len(g.lines[0]); right++ {
		if g.lines[y+1][right] != "#" && g.lines[y+1][right] != "o" {
			return false
		}
		if g.lines[y][right] == "#" {
			break
		}
		if right == len(g.lines[0]) - 1 {
			return false
		}
	}
	return true
}
// start propagation from source
func (g *ground) seed() {
	for x := 0; x < len(g.lines[0]); x++ {
		if g.lines[0][x] == "+" {
			g.propagate(coord{x,0})
		}
	}
}
func (g *ground) propagate(p coord) {
	isFloor := func (symbol string) bool {
		if symbol == "o" || symbol == "~" || symbol == "#" {
			return true
		}
		return false
	}
	x := p.x
	y := p.y
	// make "wet"
	switch g.lines[y][x] {
		case "o":
			g.lines[y][x] = "~"
		case ".":
			g.lines[y][x] = "|"
		case "+":
			// do not modify but continue propagation
		default:
			//fmt.Println("this should not happen", g.lines[y][x])
			return
	}

	var below, left, right string
	if y + 1 < len(g.lines) {
		below = g.lines[y+1][x]
	}
	if x > 0 {
		left = g.lines[y][x-1]
	}
	if x +1 < len(g.lines[0]) {
		right = g.lines[y][x+1]
	}
	if below == "." {
		g.propagate(coord{x, y+1})
	} else if below == "o" {
		g.propagate(coord{x, y+1})
		if left == "." && isFloor(g.lines[y+1][x-1]) {
			// left has floor
			g.propagate(coord{x-1, y})
		}
		if right == "." && isFloor(g.lines[y+1][x+1]) {
			// right has floor
			g.propagate(coord{x+1, y})
		}
	}
	if left == "o" {
		g.propagate(coord{x-1, y})
	}
	if left == "." && isFloor(below) {
		g.propagate(coord{x-1, y})
	}
	if right == "o" {
		g.propagate(coord{x+1, y})
	}
	if right == "." && isFloor(below) {
		g.propagate(coord{x+1, y})
	}
}
func (g *ground) markConfinedSpaces() {
	for y := len(g.lines) - 1; y > 0; y-- {
		for x := 0; x < len(g.lines[0]); x++ {
			if g.lines[y][x] == "." && g.isConfined(coord{x,y}) {
				g.lines[y][x] = "o"
			}
		}
	}
}

func main() {
	input := readInput("../input.txt")
	g := generateMap(input)
	g.markConfinedSpaces()
	g.seed()
	var wet, atRest int
	for _, line := range g.lines {
		for _, c := range line {
			if c == "|" || c == "~" {
				wet++
			}
			if c == "~" {
				atRest++
			}
		}
	}
	// g.print()
	fmt.Println("Solution for part1:", wet)
	fmt.Println("Solution for part2:", atRest)
}

func generateMap(readings []string) ground {
	clay := make(map[coord]bool)
	for _, reading := range readings {
		// x=501, y=3..7
		xy := strings.Split(reading, ", ")
		n23 := strings.Split(xy[1][2:], "..")
		n1, _ := strconv.Atoi(xy[0][2:])
		n2, _ := strconv.Atoi(n23[0])
		n3, _ := strconv.Atoi(n23[1])
		if xy[0][0] == 'x' {
			// x=n1, y=n2..n3
			for y := n2; y <= n3; y++ {
				clay[coord{n1,y}] = true
			}
		} else {
			// y=n1, x=n2..n3
			for x := n2; x <= n3; x++ {
				clay[coord{x,n1}] = true
			}
		}
	}
	min := coord{math.MaxInt32, math.MaxInt32}
	max := coord{}
	for c := range clay {
		if c.x < min.x {
			min.x = c.x
		} else if c.x > max.x {
			max.x = c.x
		}
		if c.y < min.y {
			min.y = c.y
		} else if c.y > max.y {
			max.y = c.y
		}
	}
				
	// generate ground map
	var g ground
	min.y-- // add header row with source
	for y := min.y; y <= max.y; y++ {
		var l string
		for x := min.x-1; x <= max.x+1; x++ {
			if _, hasClay := clay[coord{x, y}]; hasClay {
				l += "#"
			} else if y == min.y && x == 500 {
				l += "+"
			} else {
				l += "."
			}
		}
		g.lines = append(g.lines, strings.Split(l, ""))
	}
	return g
}