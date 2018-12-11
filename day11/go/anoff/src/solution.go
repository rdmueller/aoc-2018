package main

import (
	"fmt"
)
// tag::helpers[]
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
// end::helpers[]
func main() {
	serialNumber := 1718
	grid := createGrid(300, 300, serialNumber)
	maxPower := -999999999
	var maxCell Cell
	maxWidth := 0
	for w := 3; w < 50; w++ {
		for x := 1; x <= 300 - w; x++ {
			for y := 1; y <= 300 - w; y++ {
				sum := getPowerLevelAtSquare(grid, x, y, w)
				if sum > maxPower {
					maxPower = sum
					maxCell = Cell{x, y}
					maxWidth = w
				}
			}
		}
		if w == 3 {
			fmt.Printf("Solution part 1: %d,%d with max Power of %d\n", maxCell.x, maxCell.y, maxPower)
		}
	}
	fmt.Printf("Solution part 2: %d,%d with max Power of %d at width %d\n", maxCell.x, maxCell.y, maxPower, maxWidth)
}

// tag::grid[]
func createGrid(width int, height int, serialNumber int) [][]int {
	g := make([][]int, height)

	for y := 0; y < height; y++ {
		g[y] = make([]int, width)
		for x := 0; x < width; x++ {
			g[y][x] = getPowerLevel(&Cell{x, y}, serialNumber)
		}
	}
	return g
}
// end::grid[]
// tag::powersum[]
func getPowerLevelAtSquare(grid [][]int, x int, y int, size int) int {
	sum := 0
	for cx := 0; cx < size; cx++ {
		for cy := 0; cy < size; cy++ {
			sum += grid[y+cy][x+cx]
		}	
	}
	return sum
}
// end::powersum[]