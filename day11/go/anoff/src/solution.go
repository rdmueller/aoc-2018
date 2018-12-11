package main

import (
	"fmt"
)
type Cell struct {
	x int
	y int
}

func getRackId(c *Cell) int {
	return c.x + 10
}

func getHundreds(n int) int {
	var h int
	h = n / 100
	return h%10
}

func getPowerLevel(c *Cell, serialNumber int) int {
	power := 0
	power = getRackId(c)
	power *= c.y
	power += serialNumber
	power *= getRackId(c)
	power = getHundreds(power)
	return power - 5
}

func main() {
	boxLength := 3
	serialNumber := 1718
	maxPower := -999999999
	var maxCell Cell
	for x := 1; x <= 300 - boxLength; x++ {
		for y := 1; y <= 300 - boxLength; y++ {
			sum := 0
			for cx := 0; cx < 3; cx++ {
				for cy := 0; cy < 3; cy++ {
					sum += getPowerLevel(&Cell{cx+x, cy+y}, serialNumber)
				}	
			}
			if sum > maxPower {
				maxPower = sum
				maxCell = Cell{x, y}
			}
		}
	}
	fmt.Printf("Solution part 1: %d,%d with max Power of %d\n", maxCell.x, maxCell.y, maxPower)
}
