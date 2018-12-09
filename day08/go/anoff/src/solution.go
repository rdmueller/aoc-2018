package main

import (
	"fmt"
	"helpers"
	"strings"
	"strconv"
)

type Node struct {
	id int
	childCount int
	metaCount int
	children []int // ref by id
	meta []int
}
func main() {
	input := helpers.AggregateInputStream()
	strInts := strings.Split(input[0], " ")
	ints := make([]int, len(strInts))
	for i, s := range strInts {
		ints[i], _ = strconv.Atoi(s)
	}
	fmt.Println(strInts)
}

func parseNodes(def []int) {
	var nodes []Node
	var stack []int // queue that points to nodes that are still being progressed (stack)

	for i := 0; i < len(def); i++ {

	}
}