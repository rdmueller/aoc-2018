package main

import (
	"fmt"
)

func main() {
	input := readInput("../test.txt")
	arena := newArenaFromInput(input)
	arena.print()
	fmt.Println("Solution for part1: not yet :(")
}
