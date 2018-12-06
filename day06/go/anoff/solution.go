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

	grid := generateGrid(coords, gridYmax+1, gridXmax+1)
	
	infiniteCoords := identifyInfiniteCoords(grid)
	for i := range grid {
		fmt.Println(grid[i])
	}

	fmt.Println(infiniteCoords)
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

// returns unique elements in the 4 outer borders of the "grid"
func identifyInfiniteCoords(grid [][]uint8) []uint8 {
	m := make(map[uint8]int)
	cols := len(grid[0])-1
	rows := len(grid)-1
	// top row
	for x := 0; x < cols; x++ {
		m[grid[0][x]]++
	}
	// bottom row
	for x := 0; x < cols; x++ {
		m[grid[rows][x]]++
	}
	// "right" column
	for y := 0; y < rows; y++ {
		m[grid[y][0]]++
	}
	// "left" column
	for y := 0; y < rows; y++ {
		m[grid[y][cols]]++
	}

	var s []uint8
	for k, _ := range m {
		s = append(s, k)
	}

	return s
}
