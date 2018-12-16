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
	for i := 1; i <= 5; i++ {
		fmt.Println("\n\nStep", i)
		game.round()
		game.arena.print()
	}
	fmt.Println("Solution for part1: not yet :(")
}
