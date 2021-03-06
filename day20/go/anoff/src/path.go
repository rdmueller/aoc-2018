package main

import (
	
)
// versioned position
type vPosition struct {
	Position
	version int
}
type Path struct {
	sequence string // the NWSE sequence string
	ix int // current step in the sequence
	pos vPosition // position in the map
	area *Area
}

func NewPath(sequence string, r *Area) Path {
	var p Path
	p.sequence = sequence
	p.area = r
	p.pos = r.origin
	return p
}
// return true while more steps can be made
func (p *Path) step() bool {
	// helper to expand the map if necessary
	// 	also adjust the target position if they are affected
	expandToReach := func (dest *vPosition, intermediate *vPosition) {
		xdim, ydim := p.area.dim()
		if dest.x <= 0 {
			p.area.expand(-1, 0)
		} else if dest.y <= 0 {
			p.area.expand(0, -1)
		} else if dest.x > xdim-1 {
			p.area.expand(1, 0)
		} else if dest.y > ydim-1 {
			p.area.expand(0, 1)
		}
		p.area.alignPosition(dest)
		p.area.alignPosition(intermediate)
	}
	step := p.sequence[p.ix]
	intermediate := p.pos
	dest := p.pos
	switch step {
		// always step twice to reach an actual room and not the doors
		case 'N':
			dest.y -= 2
			intermediate.y--
		case 'E':
			dest.x += 2
			intermediate.x++
		case 'S':
			dest.y += 2
			intermediate.y++
		case 'W':
			dest.x -= 2
			intermediate.x--
	}
	//fmt.Println(dest)
	expandToReach(&dest, &intermediate)
	//fmt.Println(dest)
	if p.area.isWall(dest) || p.area.isWall(intermediate) {
		panic("Did not expect to hit a wall")
	}
	// mark the path as discovered
	p.area.markDoor(intermediate).markDoor(dest)
	p.pos = dest
	p.ix++
	if p.ix >= len(p.sequence) {
		return false
	}
	return true
}

func (p *Path) walk() *Path {
	for {
		notEnded := p.step()
		if !notEnded {
			break
		}
	}
	return p
}