package main

import (
	"fmt"
	"strings"
)

func main() {
	input := strings.Join(readInput("../input.txt"), "")
	part1(input)
	// for i := 0; i < 100; i++ {
	// 	part1("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$")
	// }
}

func exploreArea(input string) *Area {
	paths := expandPattern(input)
	area := NewArea()
	fmt.Println("Exploring", len(paths), "paths")
	for _, p := range paths {
		path := NewPath(p.toString(), &area)
		path.walk()
	}
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