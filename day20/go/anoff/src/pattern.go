package main

import (
	"strings"
)

func exandPattern(pattern string) []string {
	if pattern[0]!= '^' {
		panic("Expect pattern to start with ^")
	}
	if pattern[len(pattern)-1] != '$' {
		panic("Expect pattern to end with $")
	}
	combinations, _ := expandGroup(pattern[1:len(pattern)-1])
	return combinations
}
// (ABC|BVD|S(A|B)C)F -> []string{"ABCF", "BCDF", "SACF", "SBCF"}
func expandGroup(pattern string) ([]string, int) {
	if strings.Count(pattern, "(") > strings.Count(pattern, ")") {
		panic("Parenthesis unbalanced, should have at least as many ) as (")
	}
	var combinations []string
	activeCombinations := []string{""}
	appendToAll := func (slice []string, s string) []string {
		for i := 0; i < len(slice); i++ {
			slice[i] += s
		}
		return slice
	}
	for i := 0; i < len(pattern); i++ {
		c := string(pattern[i])
		switch c {
			case "(":
				// extract sub groups
				options, offset := expandGroup(pattern[i+1:])
				var c []string
				for _, opt := range options {
					for _, comb := range activeCombinations {
						c = append(c, comb+opt)
					}
				}
				activeCombinations = c
				i += offset
			case "|":
				combinations = append(combinations, activeCombinations...)
				activeCombinations = []string{""}
			case ")":
				combinations = append(combinations, activeCombinations...)
				return combinations, i+1
			default:
				activeCombinations = appendToAll(activeCombinations, c)
		}
	}
	combinations = append(combinations, activeCombinations...)
	return combinations, len(pattern)
}
