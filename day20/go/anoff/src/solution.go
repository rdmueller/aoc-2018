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
	walkPattern(pattern, &area, map[vPosition]bool{area.origin:true}, walk)
	area.fillWalls()
	return &area
}
type RoomScore struct {
	distance int
	room Position
	steps []*Position
}
func part1(input string) {
	area := exploreArea(input)
	fmt.Println("Area explored")
	area.print()
	walkable := area.getWalkable()
	rooms := area.getRooms()
	start := area.origin
	var farestRoom *RoomScore
	var scores []*RoomScore
	for _, r := range rooms {
		isReachable, steps := Dijkstra2D(walkable, start.Position, *r)
		if isReachable {
			rs := RoomScore{distance: len(steps), room: *r, steps: steps}
			scores = append(scores, &rs)
			if farestRoom == nil || rs.distance > farestRoom.distance {
				farestRoom = &rs
			}
		}
	}
	fmt.Println("Solution for part 1:", farestRoom.distance/2, ", for area", farestRoom.room)
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