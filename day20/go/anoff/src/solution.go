package main

import (
	"fmt"
	"strings"
)

func main() {
	input := strings.Join(readInput("../test0.txt"), "")
	paths := exandPattern(input)
	room := NewRoom()
	room.expand(2, 2).expand(-2, -2).print()


	fmt.Println("Solution for part 1:", paths)
}
