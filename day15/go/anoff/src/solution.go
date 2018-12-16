package main

import (
	"fmt"
)

func main() {
	input := readInput("../test.txt")
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	fmt.Println("Step 0")
	arena.print()
	for i := 1; i <= 1; i++ {
		game.round()
		fmt.Println("\n\nStep", i)
		game.arena.print()
	}
	fmt.Println("Solution for part1: not yet :(")
}
