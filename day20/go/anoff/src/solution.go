package main

import (
	"fmt"
	"strings"
)

func main() {
	input := strings.Join(readInput("../test0.txt"), "")
	part1(input)
}

func exploreArea(input string) *Area {
	walk := func(p *Path) {
		p.walk()
	}
	pattern := parsePattern(input)
	area := NewArea()
	walkPattern(pattern, &area, area.origin, walk)
	area.fillWalls()
	return &area
}
type RoomScore struct {
	distance int
	area Position
	steps []*Position
}
func part1(input string) {
	area := exploreArea(input)
	fmt.Println("Area explored")
	area.print()
	walkable := area.getWalkable()
	areas := area.getRooms()
	start := area.origin
	var farestRoom *RoomScore
	var scores []*RoomScore
	for _, r := range areas {
		isReachable, steps := Dijkstra2D(walkable, start, *r)
		if isReachable {
			rs := RoomScore{distance: len(steps), area: *r, steps: steps}
			scores = append(scores, &rs)
			if farestRoom == nil || rs.distance > farestRoom.distance {
				farestRoom = &rs
			}
		}
	}
	fmt.Println("Solution for part 1:", farestRoom.distance/2, ", for area", farestRoom.area)
}

func animate(path *Path) {
	for {
		fmt.Println("")
		fmt.Println("Going", string(path.sequence[path.ix]), " from", path.pos)
		notEnded := path.step()
		path.area.print()
		if !notEnded {
			break
		}
		fmt.Scanln()
	}
}