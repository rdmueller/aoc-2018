package main

import (
	"fmt"
)

func main() {
	input := readInput("../input.txt")
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	fmt.Println("Step 0")
	arena.print()
	i := 0
	for {
		//fmt.Println("\n\nStep", i)
		game.round()
		//game.arena.print()
		// fmt.Println(game.arena.getElves()[0].hp)
		if len(game.arena.getElves()) == 0 {
			fmt.Println(i-1, game.arena.getHitPoints())
			break
		}
		i++
	}
	fmt.Println("Solution for part1: not yet :(")
}

func part1(filepath string) (int, int) {
	input := readInput(filepath)
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	i := 0
	for {
		game.round()
		if len(game.arena.getElves()) == 0 {
			fmt.Println(i-1, game.arena.getHitPoints())
			return i, game.arena.getHitPoints()
		}
		i++
		if i > 100 {
			panic("This takes too long..")
		}
	}
	return -1, -1
}
