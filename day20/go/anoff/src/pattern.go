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
type Chunk struct {
	prev *Chunk
	sequence string
}
func (c *Chunk) toString() string {
	if c.prev != nil {
		return c.prev.toString() + c.sequence
	}
	return c.sequence
}
func (c *Chunk) append(s string) {
	c.sequence += s
}
func expandPattern(pattern string) []*Chunk {
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
func expandGroup(pattern *Pattern, ix int) ([]*Chunk, int) {
	var combinations []*Chunk
	activeCombinations := []*Chunk{&Chunk{sequence:""}}
	appendToAll := func (chunks []*Chunk, s string) {
		for i := 0; i < len(chunks); i++ {
			chunks[i].append(s)
		}
	}
	for ; ix < pattern.length(); ix++ {
		c := string(pattern.get(ix))
		switch c {
			case "(":
				// extract sub groups
				options, offset := expandGroup(pattern, ix+1)
				var c []*Chunk
				for _, opt := range options {
					for _, comb := range activeCombinations {
						opt.prev = comb
						c = append(c, opt)
					}
				}
				activeCombinations = c
				ix = offset
				fmt.Println("Index updated", ix)
			case "|":
				combinations = append(combinations, activeCombinations...)
				activeCombinations = []*Chunk{&Chunk{sequence:""}}
			case ")":
				combinations = append(combinations, activeCombinations...)
				return combinations, ix
			default:
				appendToAll(activeCombinations, c)
		}
	}
	combinations = append(combinations, activeCombinations...)
	return combinations, ix
}
