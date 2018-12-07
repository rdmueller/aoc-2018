package main

import (
	"fmt"
	"regexp"
	"strings"
	"sort"
	"graf"
	"helpers"
)


func main() {
	input := helpers.GetInput(false)
	edges := getEdges(input)
	nodes := getNodes(input)
	graph := graf.BuildGraph(edges, nodes)

	//part1(graph)
	part2(graph, 2)
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

type Worker struct {
	id int
	item graf.Node
	finishedAt int
}
func part2(graph graf.Graph, workerCount int) []graf.Node {
	var workers []Worker
	for i := 0; i < workerCount; i++ {
		workers = append(workers, Worker{id: i, item: graf.Node{"."}, finishedAt: 0}) // idling
	}

	var traversed []graf.Node
	var queueIds []string
	knownNodes := make(map[graf.Node]bool) // nodes that are in the queue
	startNodes := graf.FindStartNodes(graph)
	queueIds = graf.Nodes2Strings(startNodes)
	for _, n := range startNodes {
		knownNodes[n] = true
	}
	time := 0

	// print header
	fmt.Print("Second\t")
	for _, w := range workers {
		fmt.Print("Worker ", w.id, "\t")
	}
	fmt.Println("Done")

	for {
		if len(queueIds) == 0 {
			// check if all workers are idling
			allIdling := true
			for _, w := range workers {
				if w.item.Id != "." {
					allIdling = false
				}
			}
			if allIdling {
				fmt.Println("BRÄK")
				break
			}
		}
		sort.Strings(queueIds)

		for i, w := range workers {
			if w.finishedAt <= time { // can be re-assigned
				if w.finishedAt == time && w.item.Id != "."{ // has been reached now -> traversed
					traversed = append(traversed, w.item)
					childNodes := graf.GetChildNodes(graph, w.item)
					for _, n := range childNodes {
						if _, exists := knownNodes[n]; !exists {
							queueIds = append(queueIds, n.Id)
							knownNodes[n] = true
						}
					}
					workers[i].item = graf.Node{"."}
				}
				// get next reachable node
				var nextNode graf.Node
				for i := 0; i < len(queueIds); i++ {
					node := graf.Node{queueIds[i]}
					if graf.IsReachable(graph, traversed, node) {
						// pop element from queue
						nextNode = graf.Node{queueIds[i]}
						queueIds = append(queueIds[:i], queueIds[i+1:]...)
						break
					}
				}
				if nextNode.Id == "" {
					// fmt.Printf("No reachable node found for worker #%d\n", w.id)
				} else {
					workers[i].item = nextNode
					workers[i].finishedAt = time+getTaskDuration(nextNode.Id)
				}
			}
		}
		fmt.Print(time, "\t")
		for _, w := range workers {
			fmt.Print(w.item.Id, "\t\t")
		}
		fmt.Println(strings.Join(graf.Nodes2Strings(traversed), ""))
		time++
	}
	return traversed
}

func getTaskDuration(id string) int {
	return int(id[0]) - 64 + 0
}