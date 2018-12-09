package main

import (
	"fmt"
	"math/rand"
	"strings"
	"helpers"
	"strconv"
)
// tag::StructDef[]
type Node struct {
	id int
	childCount int
	metaCount int
	children []int // ref by id
	meta []int
}
// end::StructDef[]
func main() {
	input := helpers.AggregateInputStream()
	strInts := strings.Split(input[0], " ")
	ints := make([]int, len(strInts))
	for i, s := range strInts {
		ints[i], _ = strconv.Atoi(s)
	}

	nodes, root := parseNodes(ints)

	// part 1
	// tag::p1[]
	score := 0
	for _, n := range nodes {
		for _, m := range n.meta {
			score += m
		}
	}
	fmt.Println("Solution part1:", score)
	// tag::p2[]
	// part 2
	fmt.Println("Solution part2:", getValue(root, &nodes))
}

func parseNodes(def []int) ([]Node, Node) {
	var nodes []Node
	root, _ := getNode(def, &nodes)
	return nodes, root
}

// tag::recursion[]
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
// end::recursion[]
func getValue(n Node, nodes *[]Node) int {
	sum := 0
	if n.childCount == 0 {
		for _, m := range n.meta {
			sum += m
		}
	} else {
		for _, m := range n.meta {
			m--
			if m < n.childCount {
				c := getNodeById(n.children[m], *nodes)
				sum += getValue(c, nodes)
			}
		}
	}
	return sum
}

func getNodeById(id int, nodes []Node) Node {
	for _, n := range nodes {
		if n.id == id {
			return n
		}
	}
	return Node{}
}