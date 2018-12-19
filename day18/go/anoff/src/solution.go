package main

import (
	"fmt"
)
type field struct {
	x, y int
	state string // open (.), lumberyard (#), tree (|)
	neighbors []*field
	nextState string
}
func (f *field) calcNextState() *field {
	switch f.state {
		case ".":
			if f.countNeighborsOfState("|") >= 3 {
				f.nextState = "|"
			}
		case "|":
			if f.countNeighborsOfState("#") >= 3 {
				f.nextState = "#"
			}
		case "#":
			if f.countNeighborsOfState("|") < 1 || f.countNeighborsOfState("#") < 1  {
				f.nextState = "."
			}
	}
	return f
}
func (f *field) switchState() *field {
	if f.nextState != "" {
		f.state = f.nextState
	}
	return f
}
func (f *field) countNeighborsOfState(state string) int {
	count := 0
	for _, n := range f.neighbors {
		if n.state == state {
			count++
		}
	}
	return count
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
func main() {
	input := readInput("../input.txt")
	part1(input)
	part2(input)
}

func part1(input []string) {
	a := NewAreaFromInput(input)
	for minute := 0; minute < 10; minute++ {
		a.tick()
	}
	fmt.Println("Solution for part1:", a.score())
}

func part2(input []string) {
	a := NewAreaFromInput(input)
	maxExpectedPatternLength := 80
	scores := NewScoreFilter(2*maxExpectedPatternLength) // keep track of the last elements
	patternStartIndex := -1
	patternStartRound := -1
	pattern := []int{}
	for i := 0; i < 5000; i++ {
		a.tick()
		score := a.score()
		scores.add(score)
		for l := 20; l < maxExpectedPatternLength; l++ {
			patternStartIndex, pattern = scores.findRecurringPatternOfLength(l)
			if patternStartIndex != -1 {
				patternStartRound = i - 2*l + 1
				fmt.Println("Found reccuring pattern of length", l, "starting at index", patternStartIndex, "in round", patternStartRound, pattern)
				break
			}
		}
		if patternStartIndex > 0 {
			break
		}
	}

	if patternStartIndex == -1 {
		panic("No pattern found for part 2")
	}
	roundsToDo := 1000000000 - patternStartRound - 1 // not really sure why I need the -1...was desperate :(
	fmt.Println("Solution for part2:", pattern[roundsToDo % len(pattern)])
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