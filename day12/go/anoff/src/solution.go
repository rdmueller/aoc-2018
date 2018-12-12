package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

// tag::helpers[]
type Pot struct {
	id int
	left *Pot
	right *Pot
	hasPlant bool
	willHavePlant bool
}

type PropRule struct {
	id int
	pattern [5]bool
	result bool
}

type Farm struct {
	pots []Pot
	rules []PropRule
}
// end::helpers[]

func readInput(filepath string) []string {
	b, err := ioutil.ReadFile(filepath)
	if err != nil {
			panic(err)
	}

	s := string(b)
	return strings.Split(s, "\n")
}

func main() {
	input := readInput("../test.txt")
	initState := strings.Split(input[0], ": ")[1]
	var farm Farm
	farm.pots = extractPots(initState)
	farm.rules = extractPropagationRules(input[2:])
	fmt.Println(farm)
}

// tag::pots[]
// create a slice of Pots from a given input string (##.##...###...#...)
func extractPots(state string) []Pot {
	var f []Pot
	for i, c := range state {
		p := Pot{id: i}
		if c == '#' {
			p.hasPlant = true
		}
		if i > 0 {
			p.left = &f[i-1]
			f[i-1].right = &p
		}

		f = append(f, p)
	}
	return f
}
// end::pots[]

func extractPropagationRules(patterns []string) []PropRule {
	var r []PropRule
	for i, l := range patterns {
		s := strings.Split(l, " => ")
		var res bool
		if s[1] == "#" {
			res = true
		}
		var pattern [5]bool
		for ix, c := range s[0] {
			if c == '#' {
				pattern[ix] = true
			}
		}
		p := PropRule{id: i, result: res, pattern: pattern}
		r = append(r, p)
	}
	return r
}