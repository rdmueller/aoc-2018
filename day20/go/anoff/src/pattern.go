package main

import (
	"fmt"
)
type Pattern struct {
	sequence []rune
}
func (p *Pattern) get(ix int) rune {
	return p.sequence[ix]
}
func (p *Pattern) length() int {
	return len(p.sequence)
}
func expandPattern(pattern string) []string {
	if pattern[0]!= '^' {
		panic("Expect pattern to start with ^")
	}
	if pattern[len(pattern)-1] != '$' {
		panic("Expect pattern to end with $")
	}
	p := Pattern{[]rune(pattern[1:len(pattern)-1])}
	combinations, _ := expandGroup(&p, 0)
	return combinations
}
// (ABC|BVD|S(A|B)C)F -> []string{"ABCF", "BCDF", "SACF", "SBCF"}
func expandGroup(pattern *Pattern, ix int) ([]string, int) {
	var combinations []string
	activeCombinations := []string{""}
	appendToAll := func (slice []string, s string) []string {
		for i := 0; i < len(slice); i++ {
			slice[i] += s
		}
		return slice
	}
	for ; ix < pattern.length(); ix++ {
		c := string(pattern.get(ix))
		switch c {
			case "(":
				// extract sub groups
				options, offset := expandGroup(pattern, ix+1)
				var c []string
				for _, opt := range options {
					for _, comb := range activeCombinations {
						c = append(c, comb+opt)
					}
				}
				activeCombinations = c
				ix = offset
				fmt.Println("Index updated", ix)
			case "|":
				combinations = append(combinations, activeCombinations...)
				activeCombinations = []string{""}
			case ")":
				combinations = append(combinations, activeCombinations...)
				return combinations, ix
			default:
				activeCombinations = appendToAll(activeCombinations, c)
		}
	}
	combinations = append(combinations, activeCombinations...)
	return combinations, ix
}
