package main

import (
	"fmt"
	"strconv"
	"strings"
)

func main() {
	//input := 170641

	board := "37"
	cook(&board, 0, 1)

	fmt.Printf("Solution part1: %d\n", 2)
}

// cook recipe 1+2 and update the board
// 	returns index of two new recipes
func cook(board *string, recipe1 int, recipe2 int) (int, int) {
	s := strings.Split(*board, "")
	s1, _ := strconv.Atoi(s[recipe1])
	s2, _ := strconv.Atoi(s[recipe2])
	result := strconv.Itoa(s1+s2)
	*board += result
	maxRecipe := len(*board)
	r1 := (recipe1+s1+1) % maxRecipe
	r2 := (recipe2+s2+1) % maxRecipe

	return r1, r2
}