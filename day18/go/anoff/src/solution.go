package main

import (
	"fmt"
)
type field struct {
	x, y int
	state string // open (.), lumberyard (#), tree (|)
	neighbors []*field
}

type area struct {
	fields []*field
	rows [][]*field
	xmax, ymax int
}

func (a *area) print() *area {
	for _, row := range a.rows {
		for _, f := range row {
			fmt.Printf(f.state)
		}
		fmt.Println("")
	}
	return a
}
func main() {
	input := readInput("../test.txt")
	part1(input)
	part2(input)
}

func part1(input []string) {
	a := NewAreaFromInput(input)
	a.print()
	fmt.Println("Solution for part1:", 0)
}

func part2(input []string) {
	
	fmt.Println("Solution for part2:", 0)
}

func NewAreaFromInput(input []string) *area {
	var a area
	var intermediate [][]*field
	// create all fields
	for y, line := range input {
		intermediate = append(intermediate, []*field{})
		for x, c := range line {
			f := field{x:x, y:y, state:string(c)}
			intermediate[y] = append(intermediate[y], &f)
			if x > a.xmax {
				a.xmax = x
			}
		}
		if y > a.ymax {
			a.ymax = y
		}
	}
	// loop again to store neighbor relations
	for _, row := range intermediate {
		for _, f := range row {
			// bunch of helpers for nicer conditions
			var isTop, isBottom, isRight, isLeft bool
			if f.y == 0 {
				isTop = true
			}
			if f.y == a.ymax {
				isBottom = true
			}
			if f.x == 0 {
				isLeft = true
			}
			if f.x == a.xmax {
				isRight = true
			}

			// find neighbors
			if !isTop {
				f.neighbors = append(f.neighbors, intermediate[f.y-1][f.x])
				if !isRight {
					f.neighbors = append(f.neighbors, intermediate[f.y-1][f.x+1])
				}
				if !isLeft {
					f.neighbors = append(f.neighbors, intermediate[f.y-1][f.x-1])
				}
			}
			if !isBottom {
				f.neighbors = append(f.neighbors, intermediate[f.y+1][f.x])
				if !isRight {
					f.neighbors = append(f.neighbors, intermediate[f.y+1][f.x+1])
				}
				if !isLeft {
					f.neighbors = append(f.neighbors, intermediate[f.y+1][f.x-1])
				}
			}
			if !isRight {
				f.neighbors = append(f.neighbors, intermediate[f.y][f.x+1])
			}
			if !isLeft {
				f.neighbors = append(f.neighbors, intermediate[f.y][f.x-1])
			}
		}
		a.fields = append(a.fields, row...)
	}
	a.rows = intermediate
	return &a
}