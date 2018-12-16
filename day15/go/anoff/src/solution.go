package main

import (
	"fmt"
	"time"
)

func main() {
	input := readInput("../input.txt")
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	fmt.Println("Step 0")
	game.arena.print()
	i := 1
	for {
		game.round()
		fmt.Print("\u001b[2J\u001b[H") // clear screen
		fmt.Println("\n\nStep", i)
		game.arena.print()
		//fmt.Scanln()
		time.Sleep(300 * time.Millisecond)
		// fmt.Println(game.arena.getElves()[0].hp)
		if len(game.arena.getElves()) == 0 || len(game.arena.getGoblins()) == 0 {
			fmt.Println(i, game.arena.getHitPoints())
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
	i := 1
	for {
		game.round()
		if len(game.arena.getElves()) == 0 || len(game.arena.getGoblins()) == 0 {
			return i, game.arena.getHitPoints()
		}
		i++
		if i > 500 {
			panic("This takes too long..")
		}
	}
}
