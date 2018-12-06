package main

import (
	"fmt"
	"strings"
	"strconv"
)

type Coord struct {
	x uint16
	y uint16
}

func main() {
	input := getInput(false)
	
	var coords []Coord
	for _, line := range input {
		coords = append(coords, extractCoords(line))
	}
	fmt.Println(coords)
	//fmt.Printf("Solution for part 1: %d, (%s)\n", len(imploded), imploded)

	// var grid [500][500]uint16
	// gridXmax := 0 // helper to find max val
	// gridYmax := 0
}

func extractCoords(input string) Coord {
	parts := strings.Split(input, ", ")
	x, _ := strconv.Atoi(parts[0])
	y, _ := strconv.Atoi(parts[1])
	
	coord := Coord{uint16(x), uint16(y)}
	return coord
}

func manhattenDistance(x1 uint16, y1 uint16, x2 uint16, y2 uint16) uint16 {
	var diff uint16
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