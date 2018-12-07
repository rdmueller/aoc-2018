package main

import (
	"fmt"
	"regexp"
	"log"
	"sort"
	"strings"
)

type Node struct {
	id string
}
type Edge struct {
	from Node
	to Node
}
type Graph struct {
	vertices map[Node][]Node
	edges []Edge
	nodes []Node
}
func main() {
	input := getInput(false)
	edges := getEdges(input)
	nodes := getNodes(input)
	graph := createGraph(edges, nodes)
	// fmt.Println(edges)
	// fmt.Println(graph.vertices)

	walkPath(graph)
}

func getEdges(input []string) []Edge {
	var edges []Edge
	for _, line := range input {
		regex := *regexp.MustCompile(`Step (?P<to>\w+) must be finished before step (?P<from>\w+) can begin.`)
		res := regex.FindStringSubmatch(line)
		if len(res) == 3 {
			from := res[1]
			to := res[2]
			edge := Edge{from: Node{from}, to: Node{to}}
			edges = append(edges, edge)
		} else {
			fmt.Println("wooop invalid line?", line)
		}
	}

	return edges
}

func getNodes(input []string) []Node {
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
	nodes := make([]Node, 0, len(m))
	for k := range m {
		nodes = append(nodes, Node{k})
	}
	return nodes
}
func createGraph(edges []Edge, nodes []Node) Graph {
	m := make(map[Node][]Node) // from: id, to: [id1, id2..]
	for _, e := range edges {
		if _, exists := m[e.from]; exists {
			m[e.from] = append(m[e.from], e.to)
		} else {
			m[e.from] = []Node{e.to}
		}
	}
	graph := Graph{vertices: m, edges: edges, nodes: nodes}
	return graph
}

func findStartNodes(graph Graph) []Node {
	verts := graph.vertices
	nodes := graph.nodes
	var startNodes []Node
	for _, n := range nodes {
		isStartNode := true
		for _, dests := range verts {
			for _, d := range dests {
				if d == n {
					isStartNode = false
				}
			}
		} 
		if isStartNode {
			startNodes = append(startNodes, n)
		}
	}
	if len(startNodes) == 0 {
		panic("No start node found")
	}
	return startNodes
}

func findEndNode(graph Graph) Node {
	v := graph.vertices
	nodes := graph.nodes
	for _, n := range nodes {
		if _, exists := v[n]; !exists {
			return n
		}
	}
	log.Fatalln("No end node found")
	return Node{"NOOOOOO"}
}

func walkPath(graph Graph) {
	var traversed []Node
	var queueIds []string
	knownNodes := make(map[Node]bool) // nodes that are in the queue
	startNodes := findStartNodes(graph)
	fmt.Println("starting", startNodes)
	queueIds = nodes2Strings(findStartNodes(graph))
	for _, n := range startNodes {
		knownNodes[n] = true
	}
	for {
		if len(queueIds) == 0 {
			break
		}
		sort.Strings(queueIds)
		// get next reachable node
		var nextNode Node
		for i := 0; i < len(queueIds); i++ {
			node := Node{queueIds[i]}
			if isReachable(graph, traversed, node) {
				// pop first element from queue
				nextNode = Node{queueIds[i]}
				queueIds = append(queueIds[:i], queueIds[i+1:]...)
				break
			}
		}
		if nextNode.id == "" {
			fmt.Println("no reachable node found", traversed, queueIds)
			panic("adsf")
		}
		traversed = append(traversed, nextNode)
		childNodes := getChildNodes(graph, nextNode)
		for _, n := range childNodes {
			if _, exists := knownNodes[n]; !exists {
				queueIds = append(queueIds, n.id)
				knownNodes[n] = true
			}
		}
		fmt.Println(strings.Join(nodes2Strings(traversed), ""), queueIds)
	} // CABDFE (test.txt)
}

func isTraversed(traversed []Node, node Node) bool {
	for _, n := range traversed {
		if n == node {
			return true
		}
	}
	return false
}

func isReachable(graph Graph, traversed []Node, node Node) bool {
	edges := graph.edges
	isReachable := true
	for _, e := range edges {
		if e.to == node && !isTraversed(traversed, e.from) {
			isReachable = false
		}
	}
	return isReachable
}

func getChildNodes(graph Graph, node Node) []Node {
	v := graph.vertices
	for from, to := range v {
		if from == node {
			return to
		}
	}
	var empty []Node
	return empty
}

func nodes2Strings(nodes []Node) []string {
	var str []string
	for _, n := range nodes {
		str = append(str, n.id)
	}
	return str
}