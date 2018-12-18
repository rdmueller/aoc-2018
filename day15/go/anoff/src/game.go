package main

import (
	"fmt"
)

type Game struct {
	arena *Arena
	roundsPlayed int
}

// play a single fighter on the board
// 	return true if more turns are possible in this round
func (g *Game) turn() bool {
	fighter := &Fighter{}
	openFighters := 0
	for _, f := range g.arena.fighters {
		if !f.tookTurn {
			openFighters++
			if fighter.alliance == "" || (f.pos.y < fighter.pos.y || (f.pos.y == fighter.pos.y && f.pos.x < fighter.pos.x)) {
				fighter = f
			}
		}
	}
	openFighters-- // remove currently active fighter for future considerations
	fighter.tookTurn = true
	if fighter.alliance == "" {
		// no open fighter has been found
		return false
	}

	var opponents []*Fighter
	if fighter.alliance == "elves" {
		opponents = g.arena.getGoblins()
	} else if fighter.alliance == "goblins" {
		opponents = g.arena.getElves()
	}
	// move if not in any attack position
	if canAttack, _ := fighter.inAttackDistance(opponents); !canAttack {
		var shortestPath []Position
		i := 0
		for _, o := range opponents {
			for _, pos := range g.arena.getAttackPositions(o) {
				isReachable, p := g.arena.path(fighter.pos, pos)
				if isReachable {
					// valid path
					if i == 0 || len(p) < len(shortestPath) {
						shortestPath = p
					} else if len(p) == len(shortestPath) {
						// handle the top->down, left-> right priority for ties
						newPos := p[len(p)-1]
						oldPos := shortestPath[len(shortestPath) - 1]
						if newPos.y < oldPos.y || (newPos.y == oldPos.y && newPos.x < newPos.y) {
							// fmt.Println("Changing shortest path of equal length from:", shortestPath[len(shortestPath) - 1], "to:", p[len(p)-1], "fighter:", f)
							shortestPath = p
						}
					}
					i++
				}
			}
		}
		if len(shortestPath) == 0 {
			// fmt.Println("Can not reach target", f)
			if openFighters > 0 {
				return true
			}
			return false
		}
		if manhattanDistance(fighter.pos.x, fighter.pos.y, shortestPath[0].x, shortestPath[0].y) > 1 {
			// fmt.Println("Weird shortest path Oo, guess he can't move", shortestPath, f)
			if openFighters > 0 {
				return true
			}
			return false
		}
		// fmt.Println("Acceptable shortest path", shortestPath)
		fighter.move(shortestPath[0])
	}
	// check if striking is possible AFTER moving (or not moving in the first place)
	if canAttack, opponent := fighter.inAttackDistance(opponents); canAttack {
		//fmt.Println("Striking opponent..", f, opponent)
		opponent.takeDamage(fighter.power)
		// remove corpses from the field
		if opponent.hp <= 0 {
			for ix, f := range g.arena.fighters {
				if opponent.id == f.id {
					if ix < len(g.arena.fighters) -1 {
						g.arena.fighters = append(g.arena.fighters[:ix], g.arena.fighters[ix+1:]...)
					} else {
						g.arena.fighters = g.arena.fighters[:ix]
					}
				}
			}
		}
	}
	
	if openFighters > 0 {
		return true
	}
	return false
}

// runs one round, returns (true, *Game) if game was finished with open turns
func (g *Game) round() (bool, *Game) {
	for hasTurns := g.turn(); hasTurns; hasTurns = g.turn() {
		if (len(g.arena.getElves()) == 0 || len(g.arena.getGoblins()) == 0) && hasTurns {
			return true, g
		}
	}
	for _, f := range g.arena.fighters {
		f.tookTurn = false
	}
	return false, g
}
func newGame(a *Arena) Game {
	var g Game
	g.arena = a
	fmt.Print("") // dummy to keep fmt import
	return g
}