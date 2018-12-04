package main

import (
	"fmt"
	"io"
	"strings"
	"strconv"
	"sort"
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
	input := getInput()
	sort.Strings(input)
	fmt.Println(strings.Join(input, "\n"))
}

// parse all input lines into string slice
func getInput() []string {
	reader := getPipeReader()
	var input []string

	for {
		lineBytes, _, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		lineString := string(lineBytes)
		input = append(input, lineString)
	}
	return input
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
