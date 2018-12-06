package main

import (
	"fmt"
	"strings"
	"strconv"
)

type Coord struct {
	id int
	x int
	y int
}

func main() {
	input := getInput(false)
	
	var coords []Coord
	var gridXmax int // helper to find max val
	var gridYmax int
	for ix, line := range input {
		coord := extractCoords(line)
		coord.id = ix+1 // use line# as id (make sure 0 remains empty for special case)
		coords = append(coords, coord)
		if coord.x > gridXmax {
			gridXmax = coord.x
		}
		if coord.y > gridYmax {
			gridYmax = coord.y
		}
	}
	fmt.Println(coords)
	//fmt.Printf("Solution for part 1: %d, (%s)\n", len(imploded), imploded)

	grid := generateGrid(coords, 10, 10)
	
	//infiniteCoords := identifyInfiniteCoords(grid, gridXmax, gridYmax)
	for i := range grid {
		fmt.Println(grid[i])
	}
}

func extractCoords(input string) Coord {
	parts := strings.Split(input, ", ")
	x, _ := strconv.Atoi(parts[0])
	y, _ := strconv.Atoi(parts[1])
	
	coord := Coord{x: x, y: y}
	return coord
}

func manhattenDistance(x1 int, y1 int, x2 int, y2 int) int {
	var diff int
	if x1 < x2 {
		diff += x2-x1
	} else {
		diff += x1-x2
	}

	if y1 < y2 {
		diff += y2 - y1
	} else {
		diff += y1 - y2
	}

	return diff
}

func generateGrid(coords []Coord, dimX int, dimY int) [][]uint8 {
	grid := init2dSlice(dimY, dimX)
	for x := 0; x < dimX; x++ {
		for y := 0; y < dimY; y++ {
			shortestDistance := 500
			closestNeighbor := 0
			for _, coord := range coords {
				dist := manhattenDistance(coord.x, coord.y, x, y)
				if dist < shortestDistance {
					shortestDistance = dist
					closestNeighbor = coord.id
				} else if (dist == shortestDistance) {
					closestNeighbor = 0 // 0 represents a tie (multiple closest neighbors)
				}
			}
			grid[y][x] = uint8(closestNeighbor)
		}
	}
	return grid
}

func init2dSlice(d1 int, d2 int) [][]uint8 {
	s := make([][]uint8, d1)
	for i := range s {
			s[i] = make([]uint8, d2)
	}
	return s
}
/*
func identifyInfiniteCoords(grid [][]int, gridXmax, gridYmax) []int {

}
*/