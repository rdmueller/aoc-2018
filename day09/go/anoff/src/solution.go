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
	circle, _ := marbleGame(7, 25)
	fmt.Println(circle)
}

func marbleGame(players int, maxTurns int) (circle []int, scores []Score) {
	circle = make([]int, maxTurns)
	scores = make([]Score, players)

	current := 0
	circle[0] = 0
	for turn := 1; turn < maxTurns+1; turn++ {
		next := nextIndex(current, turn)
		//fmt.Println(current, turn, next)
		circle[next] = turn // also the "value" of the marble
		current = next
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