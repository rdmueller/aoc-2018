package main

import (
	"fmt"
)

func main() {
	//cave := NewCave(510, Position{10,10})
	cave := NewCave(11991, Position{6,797})
	cave.explore()
	cave.print()
	fmt.Println("Solution for part 1:", cave.riskScore())
}