package main

import (
	"fmt"
)

func main() {
	input := readInput("../test.txt")
	immune, infection := createArmiesFromInput(input)
	armies := []*Army{&immune, &infection}
	part1(armies)
}

func part1(armies []*Army) {
	turn(armies)
	fmt.Println("Solution for part1:", 0)
}
func turn(armies []*Army) {
	a1 := armies[0]
	a2 := armies[1]
	a1.planAttack(a2)
	a2.planAttack(a1)
	for _, g := range a1.groups {
		fmt.Println(g, "targeting", g.target, "with", g.damagePotential(g.target), "damage")
	}
}

func part2([]Army) {
	fmt.Println("Solution for part2:", 0)
}
