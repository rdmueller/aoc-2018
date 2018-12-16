package main

import (
	"fmt"
)

func main() {
	input := readInput("../test2.txt")
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	fmt.Println("Step 0")
	arena.print()
	for i := 1; i <= 30; i++ {
		fmt.Println("\n\nStep", i)
		game.round()
		game.arena.print()
		fmt.Println(game.arena.getElves()[0].hp)
	}
	fmt.Println("Solution for part1: not yet :(")
}
