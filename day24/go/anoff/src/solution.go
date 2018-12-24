package main

import (
	"fmt"
)

func main() {
	input := readInput("../test.txt")
	immune, infection := createArmiesFromInput(input)
	armies := []Army{immune, infection}
	part1(armies)
}

func part1([]Army) {
	fmt.Println("Solution for part1:", 0)
}

func part2([]Army) {
	fmt.Println("Solution for part2:", 0)
}
