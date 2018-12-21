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
	doSequence := func (sequence string) []*Path {
		var paths []*Path
		for s := range startPositions {
			var p Path
			start := *s
			area.alignPosition(&start)

			if len(sequence) > 0 {
				p = NewPath(sequence, area)
				p.pos = start // fix position again because p is being overwritten
				// fmt.Println("\nWalking sequence", sequence, "from", p.pos)
				// area.printPosition(p.pos)
				exec(&p)
				paths = append(paths, &p)
			}
		}
		return paths
	}
	cleanMap := func (old map[*vPosition]bool) map[*vPosition]bool {
		n := make(map[*vPosition]bool)
		seen := make(map[vPosition]bool)
		for v := range old {
			area.alignPosition(v)
			p := *v
			if exists, _ := seen[p]; !exists {
				seen[p] = true
				n[v] = true
			}
		}
		return n
	}
	startPositions = cleanMap(startPositions)
	sequence := ""
	for i := 0; i < len(pattern); i++ {
		c := string(pattern[i])
		switch c {
			case "(":
				paths := doSequence(sequence)
				// fix offsets after moving
				startPositions = make(map[*vPosition]bool)
				for _, p := range paths {
					e := p.pos
					startPositions[&e] = true
				}
				sequence = ""
				newPos, offset := walkPattern(pattern[i+1:], area, startPositions, exec)
				// only use those ends as new starts
				startPositions = make(map[*vPosition]bool)
				for p := range newPos {
					area.alignPosition(p)
					startPositions[p] = true
				}
				i += offset
				// fmt.Println("Index updated", i)
			case "|":
				paths := doSequence(sequence)
				for _, p := range paths {
					e := p.pos
					endPositions[&e] = true
				}
				sequence = ""
			case ")":
				// add the "skip this group" option
				if len(sequence) == 0 {
					for s := range startPositions {
						area.alignPosition(s)
						endPositions[s] = true
					}
				}
				paths := doSequence(sequence)
				for _, p := range paths {
					e := p.pos
					endPositions[&e] = true
				}
				sequence = ""
				endPositions = cleanMap(endPositions)
				// fmt.Println("Returning end positions as new starts")
				// for p := range endPositions {
				// 	e := *p
				// 	fmt.Println(" ", e, p)
				// }
				return endPositions, i+1
			default:
				sequence += c
		}
	}
	doSequence(sequence)
	return endPositions, -1
}