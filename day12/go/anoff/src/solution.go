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
		found := 0
		if p.id == 5 && p.hasPlant == false {
			fmt.Println(p, p.left.left)
			break
			fmt.Println(current, r.pattern)
		}
		if r.pattern == current {
			if found > 0 {
				fmt.Printf("Duplicate rule match found, previous:%t, now:%t\n", result, r.result)
			}
			// fmt.Printf("Match found for pot:%d, rule: %t, current set:%t => %t\n", p.id, r.pattern, current, r.result)
			result = r.result
			found++
		}
	}
	p.willHavePlant = result
	return p
}

func (f *Farm) propagate() *Farm {
	// calculate future plant state
	for i := range f.pots {
		f.pots[i].checkPropagation(&f.rules)
	}
	// update plant state
	for i := range f.pots {
		f.pots[i].hasPlant = f.pots[i].willHavePlant
		f.pots[i].willHavePlant = false
	}
	
	return f
}

func (f *Farm) print() {
	for _, p := range f.pots {
		if p.hasPlant {
			fmt.Print("#")
		} else {
			fmt.Print(".")
		}
	}
	fmt.Println("")
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
	farm.pots = extractPots(initState, 10)
	farm.rules = extractPropagationRules(input[2:])
	for i := 0; i < 5; i++ {
		fmt.Printf("%d: ", i)
		farm.print()
		farm.propagate()
	}
	sum := 0
	for _, p := range farm.pots {
		if p.hasPlant {
			//fmt.Printf("%d ", p.id)
			sum += p.id
		}
	}
	//fmt.Printf("Solution part1: %d\n", sum)
}

// tag::pots[]
// create a slice of Pots from a given input string (##.##...###...#...)
func extractPots(state string, padding int) []Pot {
	dummy := Pot{id: -1}
	dummy.left = &dummy
	dummy.right = &dummy
	var f []Pot
	// add padding to the right and left
	state = strings.Repeat(".", padding) + state + strings.Repeat(".", padding)
	for i, c := range state {
		p := Pot{id: i - padding}
		if c == '#' {
			p.hasPlant = true
		}
		if i > 0 {
			p.left = &f[i-1]
			// f[i-1].right = &p // TODO: Figure out why this does not work
		} else {
			p.left = &dummy
		}

		f = append(f, p)
	}
	// generate right linkage
	for i := len(f) - 2; i >= 0 ; i-- {
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