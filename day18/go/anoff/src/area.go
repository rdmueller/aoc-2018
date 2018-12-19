package main

import (
	"fmt"
)

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

func (a *area) tick() *area {
	for _, f := range a.fields {
		f.calcNextState()
	}
	for _, f := range a.fields {
		f.switchState()
	}
	return a
}
func (a *area) countFieldsOfState(state string) int {
	count := 0
	for _, f := range a.fields {
		if f.state == state {
			count++
		}
	}
	return count
}
func (a *area) score() int {
	return a.countFieldsOfState("|")*a.countFieldsOfState("#")
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