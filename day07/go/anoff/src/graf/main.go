package graf

import (
	"sort"
)

type Node struct {
	Id string
}
type Edge struct {
	From Node
	To Node
}
type Graph struct {
	Vertices map[Node][]Node
	Edges []Edge
	Nodes []Node
}

func BuildGraph(edges []Edge, nodes []Node) Graph {
	m := make(map[Node][]Node) // from: id, to: [id1, id2..]
	for _, e := range edges {
		if _, exists := m[e.From]; exists {
			m[e.From] = append(m[e.From], e.To)
		} else {
			m[e.From] = []Node{e.To}
		}
	}
	graph := Graph{Vertices: m, Edges: edges, Nodes: nodes}
	return graph
}

func FindStartNodes(graph Graph) []Node {
	verts := graph.Vertices
	nodes := graph.Nodes
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

func FindEndNodes(graph Graph) []Node {
	v := graph.Vertices
	nodes := graph.Nodes
	var endNodes []Node
	for _, n := range nodes {
		if _, exists := v[n]; !exists {
			endNodes = append(endNodes, n)
		}
	}
	if len(endNodes) == 0 {
		panic("No end node found")
	}
	return endNodes
}

func SearchSmallestIdFirst(graph Graph) []Node {
	var traversed []Node
	var queueIds []string
	knownNodes := make(map[Node]bool) // nodes that are in the queue
	startNodes := FindStartNodes(graph)
	queueIds = Nodes2Strings(startNodes)
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
			if IsReachable(graph, traversed, node) {
				// pop first element from queue
				nextNode = Node{queueIds[i]}
				queueIds = append(queueIds[:i], queueIds[i+1:]...)
				break
			}
		}
		if nextNode.Id == "" {
			panic("no reachable node found")
		}
		traversed = append(traversed, nextNode)
		childNodes := GetChildNodes(graph, nextNode)
		for _, n := range childNodes {
			if _, exists := knownNodes[n]; !exists {
				queueIds = append(queueIds, n.Id)
				knownNodes[n] = true
			}
		}
	}
	return traversed
}

func IsTraversed(traversed []Node, node Node) bool {
	for _, n := range traversed {
		if n == node {
			return true
		}
	}
	return false
}

func IsReachable(graph Graph, traversed []Node, node Node) bool {
	edges := graph.Edges
	isReachable := true
	for _, e := range edges {
		if e.To == node && !IsTraversed(traversed, e.From) {
			isReachable = false
		}
	}
	return isReachable
}

func GetChildNodes(graph Graph, node Node) []Node {
	v := graph.Vertices
	for from, to := range v {
		if from == node {
			return to
		}
	}
	var empty []Node
	return empty
}

func Nodes2Strings(nodes []Node) []string {
	var str []string
	for _, n := range nodes {
		str = append(str, n.Id)
	}
	return str
}