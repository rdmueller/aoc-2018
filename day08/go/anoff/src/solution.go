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

	state := 0 // 0=new node (header:childCount), 1=header:metaCount, 2=metaEntry
	for i, val := range def {
		switch state {
		case 0:
			nodes = append(nodes, Node{id: len(nodes), childCount: val})
			stack = append(stack, len(nodes)-1)
			parentNode := stack[len(stack)-2]
			nodes[parentNode].children = append(nodes[parentNode].children, len(nodes)-1)
			state = 1
		case 1:
			activeNode := stack[len(stack)-1]
			nodes[activeNode].metaCount = val
			if nodes[activeNode].childCount > 0 {
				state = 0
			} else {
				state = 2
			}
		case 2:
			activeNode := stack[len(stack)-1]
			nodes[activeNode].meta = append(nodes[activeNode].meta, val)
			if len(nodes[activeNode].meta) == nodes[activeNode].metaCount {
				// all meta values read; pop node from open stack
				stack = stack[:len(stack)-1]
				nextNode := stack[len(stack)-1]
				if nodes[nextNode].childCount == len(nodes[nextNode].children) {
					// all children handled
					
				}
			} else 
		}
	}
}