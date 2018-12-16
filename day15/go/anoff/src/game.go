package main

import (
	"fmt"
)

type Game struct {
	arena *Arena
	roundsPlayed int
}

func (g *Game) round() *Game {
	for x, line := range g.arena.lines {
		for y, _ := range line {
			if isOccupied, f := g.arena.isOccupied(Position{x, y}); isOccupied {
				var opponents []*Fighter
				if f.alliance == "elves" {
					opponents = g.arena.getGoblins()[1:2]
				/*} else if f.alliance == "goblins" {
					opponents = g.arena.getElves()
				*/} else {
					// in case the tile is occupied by a non-fighter (e.g. wall)
					continue
				}

				var shortestPath []Position
				for i, o := range opponents {
					g.arena.
					// TODO: for each check all attackable positions
					p := g.arena.path(f.pos, o.pos)
					if i == 0 || len(p) < len(shortestPath) {
						shortestPath = p
					} else if len(p) == len(shortestPath) {
						// handle the top->down, left-> right priority for ties
						// TODO :D
					}
				}
				if manhattanDistance(f.pos.x, f.pos.y, shortestPath[0].x, shortestPath[0].y) > 1 {
					fmt.Println("Weird shortest path Oo", shortestPath)
				} else {
					fmt.Println("Acceptable shortest path", shortestPath)
				}
			}
		}
	}

	return g
}
func newGame(a *Arena) Game {
	var g Game
	g.arena = a
	return g
}