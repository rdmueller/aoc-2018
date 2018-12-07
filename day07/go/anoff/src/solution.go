package main

import (
	"fmt"
	"regexp"
	"strings"
	"graf"
	"helpers"
)


func main() {
	input := helpers.GetInput(false)
	edges := getEdges(input)
	nodes := getNodes(input)
	graph := graf.BuildGraph(edges, nodes)

	part1(graph)
}

func getEdges(input []string) []graf.Edge {
	var edges []graf.Edge
	for _, line := range input {
		regex := *regexp.MustCompile(`Step (?P<to>\w+) must be finished before step (?P<from>\w+) can begin.`)
		res := regex.FindStringSubmatch(line)
		if len(res) == 3 {
			from := res[1]
			to := res[2]
			edge := graf.Edge{From: graf.Node{from}, To: graf.Node{to}}
			edges = append(edges, edge)
		} else {
			fmt.Println("wooop invalid line?", line)
		}
	}

	return edges
}

func getNodes(input []string) []graf.Node {
	m := make(map[string]bool)
	for _, line := range input {
		regex := *regexp.MustCompile(`Step (?P<to>\w+) must be finished before step (?P<from>\w+) can begin.`)
		res := regex.FindStringSubmatch(line)
		if len(res) == 3 {
			from := res[2]
			to := res[1]
			m[from] = true
			m[to] = true
		} else {
			fmt.Println("wooop invalid line?", line)
		}
	}
	nodes := make([]graf.Node, 0, len(m))
	for k := range m {
		nodes = append(nodes, graf.Node{k})
	}
	return nodes
}

func part1(graph graf.Graph) {
	path := graf.SearchSmallestIdFirst(graph)
	fmt.Println("Solution part1:", strings.Join(graf.Nodes2Strings(path), ""))
}

func part2() {

}