package main

import (
	"fmt"
	"io"
	"strings"
	"strconv"
)

// tag::StructDef[]
type Area struct {
	id string
	x1 int // area start from left
	x2 int // last valid point in the area from left
	y1 int // first valid point from top
	y2 int // last valid point from top
}
// end::StructDef[]

func main() {
	reader := getPipeReader()

	// init part 1
	spaceMap := make(map[string]int)
	overlapCount := 0

	for {
		lineBytes, _, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		lineString := string(lineBytes)
		area := stringToArea(lineString)

		populateMap(&spaceMap, area)
	}
	for _, v := range spaceMap {
		if v >= 2 {
			overlapCount++
		}
	}
	fmt.Printf("Solution for part1: %d\n", overlapCount)
}

// tag::StringSplit[]
// convert an input string to a struct of Area
func stringToArea(str string) Area {
	id := strings.Split(str, " @ ")[0]
	startPos := strings.Split(strings.Split(strings.Split(str, " @ ")[1], ": ")[0], ",")
	width := strings.Split(strings.Split(strings.Split(str, " @ ")[1], ": ")[1], "x")
	x1, _ := strconv.Atoi(startPos[0])
	y1, _ := strconv.Atoi(startPos[1])
	xw, _ := strconv.Atoi(width[0])
	x2 		:= x1 + xw - 1
	yw, _ := strconv.Atoi(width[1])
	y2 		:= y1 + yw - 1

	return Area{id:id, x1:x1, x2:x2, y1:y1, y2:y2}
}
// end::StringSplit[]

func populateMap(mp *map[string]int, area Area) {
	m := *mp
	for x := area.x1; x <= area.x2; x++ {
		for y := area.y1; y <= area.y2; y++ {
			key := strconv.Itoa(x) + "_" + strconv.Itoa(y)
			m[key]++
		}
	}
}