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
	return pattern[1:len(pattern)-1]
}

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
