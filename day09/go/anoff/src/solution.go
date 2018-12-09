package main

import (
	"fmt"
)
type Score struct {
	player int
	score int
}
func main() {
	fmt.Println("asdf")
	circle, scores := marbleGame(7, 25)
	fmt.Println(circle, getHighscore(scores))
}

func marbleGame(players int, maxTurns int) (circle []int, scores []Score) {
	scores = make([]Score, players)

	current := 0
	circle = append(circle, 0)
	for turn := 1; turn < maxTurns+1; turn++ {
		if turn%23 > 0 {
			current = nextIndex(current, turn)
			circle = append(circle, -1)
			if current < turn { // shift slice around to allow "insert" in the middle of the slice
				copy(circle[current+1:], circle[current:])
			}
			circle[current] = turn // also the "value" of the marble
		} else {
			// special elve case %23
			currentPlayer := (turn%players) -1 // index
			targetMarble := current - 7
			if targetMarble < 0 {
				targetMarble =+ turn
			} else if targetMarble > turn {
				targetMarble =- turn 
			}
			scores[currentPlayer].score += 23 + circle[targetMarble]

			// handle marble removal
			circle = append(circle[:targetMarble], circle[targetMarble+1:]...)
			current = targetMarble
		}
	}
	return circle, scores
}
func nextIndex(current int, turn int) int {
	maxIndex := turn
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