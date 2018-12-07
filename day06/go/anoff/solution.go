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
	// fmt.Println(coords)

	part1(coords, gridXmax, gridYmax)

	part2(coords, gridXmax, gridYmax)
}

func part1(coords []Coord, gridXmax int, gridYmax int) {
	grid := generateGrid1(coords, gridXmax+1, gridYmax+1)
	
	infiniteElms:= identifyInfiniteElements(grid)

	occurences := identifyGridOccupation(grid)

	// find max occurence that is not an infinite group
	maxVal := 0
	maxElm := 0
	for k, v := range occurences {
		if v > maxVal {
			isFinite := true
			for _, inf := range infiniteElms {
				if inf == k {
					isFinite = false
				}
			}
			if isFinite {
				maxVal = v
				maxElm = int(k)
			}
		}
	}
	// for i := range grid {
	// 	fmt.Println(grid[i])
	// }

	// fmt.Println(infiniteElms)
	// fmt.Println(occurences)

	fmt.Printf("Solution for part 1: %d occurences of type #%d\n", maxVal, maxElm)
}

func part2(coords []Coord, cols int, rows int) {
	locationCountBelow10000 := 0
	for x := 0; x < cols; x++ {
		for y := 0; y < rows; y++ {
			distSum := 0
			for _, coord := range coords {
				dist := manhattenDistance(x, y, coord.x, coord.y)
				distSum += dist
			}
			if distSum < 10000 {
				locationCountBelow10000++
			}
		}
	}

	fmt.Printf("Solution for part 2: %d locations\n", locationCountBelow10000)
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

// generate a 2D slice (grid) with solution1 mapping (value = closest coord#)
func generateGrid1(coords []Coord, dimX int, dimY int) [][]uint8 {
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
func identifyInfiniteElements(grid [][]uint8) []uint8 {
	m := make(map[uint8]int)
	cols := len(grid[0])
	rows := len(grid)
	// top row
	for x := 0; x < cols; x++ {
		m[grid[0][x]]++
	}
	// bottom row
	for x := 0; x < cols; x++ {
		m[grid[rows-1][x]]++
	}
	// "right" column
	for y := 0; y < rows; y++ {
		m[grid[y][0]]++
	}
	// "left" column
	for y := 0; y < rows; y++ {
		m[grid[y][cols-1]]++
	}

	var s []uint8
	for k, _ := range m {
		s = append(s, k)
	}

	return s
}

// calculate a map of how often elements in the grid occur in total
func identifyGridOccupation(grid [][]uint8) map[uint8]int {
	m := make(map[uint8]int)
	cols := len(grid[0])
	rows := len(grid)

	for x := 0; x < cols; x++ {
		for y := 0; y < rows; y++ {
			m[grid[y][x]]++
		}
	}
	return m
}