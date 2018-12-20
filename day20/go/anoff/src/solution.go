package main

import (
	"fmt"
	"strings"
)

func main() {
	input := strings.Join(readInput("../test0.txt"), "")
	part1(input)
	fmt.Println("Solution for part 1:", 0)
}

func part1(input string) {
	paths := expandPattern(input)
	room := NewRoom()
	for _, p := range paths {
		fmt.Println(p)
		path := NewPath(p, &room)
		path.walk()
	}
	room.fillWalls()
	room.print()
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