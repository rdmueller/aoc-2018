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

func exploreRoom(input string) *Room {
	paths := expandPattern(input)
	room := NewRoom()
	fmt.Println("Exploring", len(paths), "paths")
	for _, p := range paths {
		path := NewPath(p.toString(), &room)
		path.walk()
	}
	room.fillWalls()
	return &room
}
type RoomScore struct {
	distance int
	room Position
	steps []*Position
}
func part1(input string) {
	room := exploreRoom(input)
	fmt.Println("Room explored")
	walkable := room.getWalkable()
	rooms := room.getRooms()
	start := room.origin
	var farestRoom *RoomScore
	var scores []*RoomScore
	for _, r := range rooms {
		isReachable, steps := Dijkstra2D(walkable, start, *r)
		if isReachable {
			rs := RoomScore{distance: len(steps), room: *r, steps: steps}
			scores = append(scores, &rs)
			if farestRoom == nil || rs.distance > farestRoom.distance {
				farestRoom = &rs
			}
		}
	}
	fmt.Println("Solution for part 1:", farestRoom.distance/2, ", for room", farestRoom.room)
}

func animate(path *Path) {
	for {
		fmt.Println("")
		fmt.Println("Going", string(path.sequence[path.ix]), " from", path.pos)
		notEnded := path.step()
		path.room.print()
		if !notEnded {
			break
		}
		fmt.Scanln()
	}
}