package main

import (
	"fmt"
	"time"
)

func main() {
	input := readInput("../input.txt")
	rounds, hp := part1(input)
	//animate(input)
	fmt.Println("Solution for part1:", rounds*hp, "(Rounds:", rounds, "HP:", hp, ")")
}

func animate(input []string) {
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	fmt.Println("Step 0")
	game.arena.print(true)
	i := 0
	for {
		hasOpenTurns, _ := game.round()
		//fmt.Print("\u001b[2J\u001b[H") // clear screen
		fmt.Println("\n\nStep", i)
		game.arena.print(true)
		//fmt.Scanln()
		time.Sleep(0 * time.Millisecond)
		if len(game.arena.getElves()) == 0 || len(game.arena.getGoblins()) == 0 {
			if !hasOpenTurns {
				i++
			}
			fmt.Println("Round", i, "Remaining HP", game.arena.getHitPoints())
			break
		}
		i++
	}
}
func part1(input []string) (int, int) {
	arena := newArenaFromInput(input)
	game := newGame(&arena)
	i := 0
	for {
		hasOpenTurns, _ := game.round()
		if len(game.arena.getElves()) == 0 || len(game.arena.getGoblins()) == 0 {
			if !hasOpenTurns {
				fmt.Println("Was final blow!")
				i++ // increase round count by one if the previous round in case this was the very last move to end the round
			}
			return i, game.arena.getHitPoints()
		}
		i++
		if i > 500 {
			panic("This takes too long..")
		}
	}
}

/* Tested answers

74 2653 = 196322
47 590 = 27730
70 2653 = 209587
67 2704 = 181168
*/