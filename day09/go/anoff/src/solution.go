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
	circle, scores := marbleGame(1, 5)
	fmt.Println(len(circle), scores)
}

func marbleGame(players int, maxTurns int) (circle []int, scores []Score) {
	circle = make([]int, maxTurns)
	scores = make([]Score, players)

	return circle, scores
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