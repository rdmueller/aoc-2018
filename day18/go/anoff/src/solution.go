package main

import (
	"fmt"
)
// tag::def[struct]
type field struct {
	x, y int
	state string // open (.), lumberyard (#), tree (|)
	neighbors []*field
	nextState string // used to calculate all future states before applying
}
// end::def[struct]
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
