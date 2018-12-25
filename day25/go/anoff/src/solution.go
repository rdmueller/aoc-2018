package main

import (
	"fmt"
	"strings"
	_"math"
)

func main() {
	input := readInput("../test0.txt")
	points := parseInput(input)
	part1(points)
	fmt.Println(input)
}

func part1(points []Position4) {

	fmt.Println("Solution for part1:", 0)
}

func parseInput(input []string) []Position4 {
	var points []Position4
	for _, line := range input {
		strNums := strings.Split(line, ",")
		nums := StringSlice2IntSlice(strNums)
		var p = Position4{nums[0], nums[1], nums[2], nums[3]}
		points = append(points, p)
	}
	return points
}