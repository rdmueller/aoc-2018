package main

import (
	"fmt"
	_"time"
)

func main() {
	input := readInput("../test4.txt")
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	fmt.Println("Step 0")
	arena.print()
	i := 0
	for {
		game.round()
		fmt.Print("\u001b[2J\u001b[H")
		fmt.Println("\n\nStep", i)
		game.arena.print()
		//fmt.Scanln()
		// time.Sleep(100 * time.Millisecond)
		// fmt.Println(game.arena.getElves()[0].hp)
		if len(game.arena.getElves()) == 0 {
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
	i := 0
	for {
		game.round()
		if len(game.arena.getElves()) == 0 {
			return i, game.arena.getHitPoints()
		}
		i++
		if i > 500 {
			panic("This takes too long..")
		}
	}
	return -1, -1
}
