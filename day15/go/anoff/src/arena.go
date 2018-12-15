package main

import (
	"strings"
	"fmt"
)

type Arena struct {
	lines []string
	fighters []*Fighter
}

func (a *Arena) addFighter(f *Fighter) *Arena {
	a.fighters = append(a.fighters, f)
	return a
}

func (a *Arena) getGoblins() []*Fighter {
	var gs []*Fighter
	for _, f := range a.fighters {
		if f.alliance == "goblins" {
			gs = append(gs, f)
		}
	}
	return gs
}

func (a *Arena) getElves() []*Fighter {
	var es []*Fighter
	for _, f := range a.fighters {
		if f.alliance == "elves" {
			es = append(es, f)
		}
	}
	return es
}

func (a *Arena) isOccupied(pos Position) (bool, *Fighter) {
	mapType := a.lines[pos.y][pos.x] // TODO: might cause out of bounds
	if mapType != '.' {
		return true, &Fighter{}
	}
	for _, f := range a.fighters {
		if f.pos.IsEqual(pos) {
			return true, f
		}
	}
	return false, &Fighter{}
}

func (a *Arena) print() *Arena {
	for y, line := range a.lines {
		for x, c := range line {
			if isOccupied, f := a.isOccupied(Position{x, y}); isOccupied {
				if f.alliance == "goblins" {
					fmt.Print("G")
				} else if f.alliance == "elves" {
					fmt.Print("F")
				} else {
					fmt.Print(string(c))
				}
			} else {
				fmt.Print(string(c))
			}
		}
		fmt.Println("")
	}
	return a
}
func newArenaFromInput(input []string) Arena {
	var a Arena
	for y, line := range input {
		for x, char := range line {
			if char == 'G' {
				f := NewFighter("goblins")
				f.move(Position{x, y})
				a.addFighter(&f)
			} else if char == 'E' {
				f := NewFighter("elves")
				f.move(Position{x, y})
				a.addFighter(&f)
			}
		}
		line = strings.Replace(line, "E", ".", -1)
		line = strings.Replace(line, "G", ".", -1)
		a.lines = append(a.lines, line)
	}
	return a
}
