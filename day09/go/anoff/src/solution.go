package main

import (
	"fmt"
	"time"
)
type Score struct {
	player int
	score int
}
func main() {
	_, scores := marbleGame(477, 70851*100)
	fmt.Println("Solution for part1: ", getHighscore(scores))
}

func marbleGame(players int, maxTurns int) (circle []int, scores []Score) {
	scores = make([]Score, players)
	start := time.Now()
	current := 0
	circle = append(circle, 0)
	circleLength := 1
	for turn := 1; turn < maxTurns+1; turn++ {
		if turn%10000 ==0 {
			fmt.Printf("Turn: %d, elapsed: %ds\n", turn, int(time.Now().Sub(start)/1e9))
		}
		if turn%23 > 0 {
			current = nextIndex(current, circleLength)
			//fmt.Println(turn, circleLength, current)
			circle = append(circle, -1)
			if current < circleLength { // shift slice around to allow "insert" in the middle of the slice
				copy(circle[current+1:], circle[current:])
			}
			circle[current] = turn // also the "value" of the marble
			circleLength++
		} else {
			// special elve case %23
			currentPlayer := (turn-1)%players // index
			targetMarble := current - 7
			if targetMarble < 0 {
				targetMarble += circleLength
			} else if targetMarble >= circleLength {
				targetMarble -= circleLength 
			}
			scores[currentPlayer].score += turn + circle[targetMarble]

			// handle marble removal
			circle = append(circle[:targetMarble], circle[targetMarble+1:]...)
			current = targetMarble
			circleLength--
		}
		/*
		fmt.Printf("[%d] ", (turn-1)%players+1)
		for i := range circle {
			if i == current {
				fmt.Printf(" (%d)", circle[i])
			} else {
				fmt.Printf(" %d", circle[i])
			}
		}
		fmt.Println("")*/
	}
	return circle, scores
}
func nextIndex(current int, circleLength int) int {
	maxIndex := circleLength
	next := current+2
	if next > maxIndex {
		return 1
	}
	return next
}
func getHighscore(scores []Score) int {
	highscore := 0
	for _, s := range scores {
		if s.score > highscore {
			highscore = s.score
		}
	}
	return highscore
}