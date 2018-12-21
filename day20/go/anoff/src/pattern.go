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
func walkPattern(pattern string, area *Area, startPositions map[*vPosition]bool, exec func(*Path)) (map[*vPosition]bool, int) {
	endPositions := make(map[*vPosition]bool)
	doSequence := func (sequence string) []Path {
		var paths []Path
		for s := range startPositions {
			var p Path
			area.alignPosition(s)
			p.pos = *s // empty path should also be added with correct start pos

			if len(sequence) > 0 {
				// fmt.Println("\nWalking sequence", sequence, "from", p.pos)
				// area.printPosition(p.pos)
				p = NewPath(sequence, area)
				p.pos = *s // fix position again because p is being overwritten
				exec(&p)
			}
			paths = append(paths, p)
		}
		return paths
	}
	sequence := ""
	for i := 0; i < len(pattern); i++ {
		c := string(pattern[i])
		switch c {
			case "(":
				paths := doSequence(sequence)
				startPositions = make(map[*vPosition]bool)
				for _, p := range paths {
					startPositions[&p.pos] = true
				}
				// start = p.pos // update position after walking the sequence
				sequence = ""
				newPos, offset := walkPattern(pattern[i+1:], area, startPositions, exec)
				for p := range newPos {
					endPositions[p] = true
				}
				i += offset
				// fmt.Println("Index updated", i)
			case "|":
				paths := doSequence(sequence)
				for _, p := range paths {
					endPositions[&p.pos] = true
				}
				sequence = ""
			case ")":
				// add the "skip this group" option
				if len(sequence) == 0 {
					for s := range startPositions {
						endPositions[s] = true
					}
				}
				doSequence(sequence)
				sequence = ""
				return endPositions, i
			default:
				sequence += c
		}
	}

	return endPositions, -1
}