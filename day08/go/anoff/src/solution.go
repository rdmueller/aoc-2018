package main

import (
	"fmt"
	"math/rand"
	"strings"
	"helpers"
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

	parseNodes(ints)
}

func parseNodes(def []int) {
	var nodes []Node

	getNode(def, &nodes)
	score := 0
	for _, n := range nodes {
		for _, m := range n.meta {
			score += m
		}
	}
	fmt.Println("Solution part1:", score)
}

func getNode(def []int, nodes *[]Node) (Node, int) {
	n := Node{id: rand.Int(), childCount: def[0], metaCount: def[1]}
	offset := 2
	for i := 0; i < n.childCount; i++ {
		child, additionalOffset := getNode(def[offset:], nodes)
		n.children = append(n.children, child.id)
		offset += additionalOffset
	}
	for i := 0; i < n.metaCount; i++ {
		n.meta = append(n.meta, def[offset])
		offset++
	}
	*nodes = append(*nodes, n)
	return n, offset
}