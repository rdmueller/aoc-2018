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

func (a *Arena) print(showStats bool) *Arena {
	for y, line := range a.lines {
		var fighters []*Fighter
		for x, c := range line {
			if isOccupied, f := a.isOccupied(Position{x, y}); isOccupied {
				if f.alliance == "goblins" {
					fighters = append(fighters, f)
					fmt.Print("G")
				} else if f.alliance == "elves" {
					fighters = append(fighters, f)
					fmt.Print("E")
				} else {
					fmt.Print(string(c))
				}
			} else {
				fmt.Print(string(c))
			}
		}
		if showStats && len(fighters) > 0 {
			for _, f := range fighters {
				fmt.Printf(" %s(%d)", strings.ToUpper(strings.Split(f.alliance, "")[0]), f.hp)
			}
		}
		fmt.Println("")
	}
	return a
}
func (a *Arena) path(start Position, dest Position) (bool, []Position) {
	var walkable []Position
	for x, line := range a.lines {
		for y, _ := range line {
			if isOccupied, _ := a.isOccupied(Position{x, y}); !isOccupied {
				walkable = append(walkable, Position{x, y})
			}
		}
	}
	walkable = append(walkable, start)
	walkable = append(walkable, dest)
	return Dijkstra2D(walkable, start, dest)
}

func (a *Arena) getAttackPositions(f *Fighter) []Position {
	var positions []Position
	possiblePositions := []Position{
		Position{f.pos.x-1, f.pos.y},
		Position{f.pos.x+1, f.pos.y},
		Position{f.pos.x, f.pos.y-1},
		Position{f.pos.x, f.pos.y+1},
	}
	for _, p := range possiblePositions {
		if isOccupied, _ := a.isOccupied(p); !isOccupied {
			positions = append(positions, p)
		}
	}
	return positions
}

func (a *Arena) getHitPoints() int {
	sum := 0
	for _, f := range a.fighters {
		if f.hp > 0 {
			sum += f.hp
		}
	}
	return sum
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
