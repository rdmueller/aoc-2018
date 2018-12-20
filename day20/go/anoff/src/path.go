package main

import (
)

type Path struct {
	sequence string // the NWSE sequence string
	ix int // current step in the sequence
	pos Pos // position in the room
	room *Room
}

func NewPath(sequence string, r *Room) Path {
	var p Path
	p.sequence = sequence
	p.room = r
	p.pos = r.origin
	return p
}
// return true while more steps can be made
func (p *Path) step() bool {
	// helper to expand the room if necessary
	expandToReach := func (dest Pos) {
		xdim, ydim := p.room.dim()
		if dest.x < 0 {
			p.room.expand(-1, 0)
		} else if dest.y < 0 {
			p.room.expand(0, -1)
		} else if dest.x > xdim-1 {
			p.room.expand(1, 0)
		} else if dest.y > ydim-1 {
			p.room.expand(0, 1)
		}
	}
	step := p.sequence[p.ix]
	dest := p.pos
	switch step {
		// always step twice to reach an actual room and not the doors
		case 'N':
			dest.y -= 2
		case 'E':
			dest.x += 2
		case 'S':
			dest.y += 2
		case 'W':
			dest.x -= 2
	}
	expandToReach(dest)
	if p.room.isWall(dest) {
		panic("Did not expect to hit a wall")
	}
	p.ix++
	if p.ix >= len(p.sequence) -1 {
		return false
	}
	return true
}
