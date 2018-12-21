package main

import (
	"fmt"
)
func parsePattern(pattern string) string {
	if pattern[0]!= '^' {
		panic("Expect pattern to start with ^")
	}
	if pattern[len(pattern)-1] != '$' {
		panic("Expect pattern to end with $")
	}
	fmt.Print()
	return pattern[1:len(pattern)-1]
}

// return new position and offset in string
func walkPattern(pattern string, area *Area, start Position, exec func(*Path)) (Position, int) {
	doSequence := func (sequence string) Path {
		var p Path
		if len(sequence) > 0 {
			// fmt.Println("\nWalking sequence", sequence, "from", start)
			// area.printPosition(start)
			p = NewPath(sequence, area)
			p.pos = start
			exec(&p)
		}
		return p
	}
	sequence := ""
	for i := 0; i < len(pattern); i++ {
		c := string(pattern[i])
		switch c {
			case "(":
				p := doSequence(sequence)
				start = p.pos // update position after walking the sequence
				sequence = ""
				newPos, offset := walkPattern(pattern[i+1:], area, start, exec)
				start = newPos
				i += offset
				// fmt.Println("Index updated", i)
			case "|":
				// if map expanded, move the position by the delta of origin as well
				var pOrigin Position
				pOrigin = area.origin
				doSequence(sequence)
				// align to changed origin if applicable
				if !area.origin.IsEqual(pOrigin) {
					//fmt.Println("fixing offset")
					start.x -= pOrigin.x - area.origin.x
					start.y -= pOrigin.y - area.origin.y
				}
				sequence = ""
			case ")":
				doSequence(sequence)
				sequence = ""
				return start, i
			default:
				sequence += c
		}
	}

	return Position{-1, -1}, -1
}