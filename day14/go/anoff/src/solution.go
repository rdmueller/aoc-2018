package main

import (
	"fmt"
)

func main() {
	input := 170641

	board := []int{3, 7}
	r1 := 0
	r2 := 1
	for {
		board, r1, r2 = cook(board, r1, r2)
		if len(board) > input+10 {
			break
		}
	}
	b10 := board[input:input+10]
	fmt.Printf("Solution part1: %d\n", b10)

	// part 2
	targetScore := []int{1,7,0,6,4,1}
	board = []int{3, 7}
	r1 = 0
	r2 = 1
	for {
		board, r1, r2 = cook(board, r1, r2)
		if len(board) > len(targetScore) {
			scoreFound := true
			for ix := range targetScore {
				if board[len(board) - 1 - (len(targetScore) - ix)] != targetScore[ix] {
					scoreFound = false
					break
				}
			}
			if scoreFound {
				break
			}
		}
	}
	fmt.Printf("Solution for part2: %d\n", len(board) - len(targetScore) - 1)
}

// tag::cook[]
// cook recipe 1+2 and update the board
// 	returns index of two new recipes
func cook(board []int, recipe1 int, recipe2 int) ([]int, int, int) {
	s1 := board[recipe1]
	s2 := board[recipe2]
	result := s1+s2
	if result > 9 {
		board = append(board, result/10)
	}
	board = append(board, (result%10))
	maxRecipe := len(board)
	r1 := (recipe1+s1+1) % maxRecipe
	r2 := (recipe2+s2+1) % maxRecipe

	return board, r1, r2
}
// end::cook[]
