package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

// tag::structs[]
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
// end::structs[]

// calculate the willHavePlant property
func (p *Pot) checkPropagation(rules *[]PropRule) *Pot {
	current := [5]bool{p.left.left.hasPlant, p.left.hasPlant, p.hasPlant, p.right.hasPlant, p.right.right.hasPlant}
	result := false
	for _, r := range *rules {
		if r.pattern == current {
			result = r.result
		}
	}
	p.willHavePlant = result
	return p
}

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
	fmt.Println(farm.pots[len(farm.pots)-1].right.right)
}

// tag::pots[]
// create a slice of Pots from a given input string (##.##...###...#...)
func extractPots(state string) []Pot {
	dummy := Pot{id: -1}
	dummy.left = &dummy
	dummy.right = &dummy
	var f []Pot
	for i, c := range state {
		p := Pot{id: i}
		if c == '#' {
			p.hasPlant = true
		}
		if i > 0 {
			p.left = &f[i-1]
		} else {
			p.left = &dummy
		}

		f = append(f, p)
	}
	// generate right linkage
	for i := 0; i < len(f) - 1; i++ {
		f[i].right = &f[i+1]
	}
	f[len(f)-1].right = &dummy
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