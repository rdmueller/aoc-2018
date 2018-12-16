package main

import (
	"math"
	"fmt"
)

type node struct {
	pos Position
	prev *node
	distance int
}
func newNode(pos Position) node {
	return node{pos:pos, distance:math.MaxInt32}
}
type grid struct {
	nodes []*node
}
func (g *grid) addNode(n *node) *grid {
	g.nodes = append(g.nodes, n)
	return g
}

func (g *grid) getShortest() *node {
	shortestN := g.nodes[0]
	shortestDistance := g.nodes[0].distance
	for _, n := range g.nodes {
		if n.distance < shortestDistance {
			shortestDistance = n.distance
			shortestN = n
		}
	}
	return shortestN
}

func (g *grid) remove(d *node) *grid {
	for i, n := range g.nodes {
		if n.pos.IsEqual(d.pos) {
			if i >= len(g.nodes) - 1{
				g.nodes = g.nodes[:i]
			} else {
				g.nodes = append(g.nodes[:i], g.nodes[i+1:]...)
			}
		}
	}
	return g
}

func (g *grid) getNeighbors(d *node) []*node {
	var neighbors []*node
	for _, n := range g.nodes {
		d := manhattanDistance(d.pos.x, d.pos.y, n.pos.x, n.pos.y)
		if d == 1 {
			neighbors = append(neighbors, n)
		}
	}
	return neighbors
}
// a dijkstra implementation that only allows simple 2D movement (non diagonal)
// https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode
func Dijkstra2D(walkable []Position, start Position, dest Position) []Position {
	var Q grid
	for _, p := range walkable {
		n := newNode(p)
		if p.IsEqual(start) {
			n.distance = 0
		}
		Q.addNode(&n)
	}
	
	for {
		u := Q.getShortest()
		Q.remove(u)
		if u.pos.IsEqual(dest) {
			steps := []Position{u.pos}
			for p := u.prev; p.prev != nil; p = p.prev {
				steps = append([]Position{p.pos}, steps...)
			}
			return steps
		}
		for _, n := range Q.getNeighbors(u) {
			var newDist int
			// prioritise horizontal movements (penalty verticals by 2 distance)
			if n.pos.y != u.pos.y {
				newDist = u.distance + 2
			} else {
				newDist = u.distance + 1
			}
			if newDist < n.distance {
				n.distance = newDist
				n.prev = u
			}
		}
		if len(Q.nodes) == 0 {
			return []Position{Position{-1, -1}}
		}
	}
}

func manhattanDistance(x1 int, y1 int, x2 int, y2 int) int {
	var diff int
	if x1 < x2 {
		diff += x2-x1
	} else {
		diff += x1-x2
	}

	if y1 < y2 {
		diff += y2 - y1
	} else {
		diff += y1 - y2
	}

	return diff
}

func asdf() {
	fmt.Println("")
}