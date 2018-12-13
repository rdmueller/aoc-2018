package main

import (
	"fmt"
	"io/ioutil"
	"strings"
	"container/list"
)

// tag::structs[]
type Pot struct {
	id int
	hasPlant bool
	willHavePlant bool
}

type PropRule struct {
	id int
	pattern [5]bool
	result bool
}

type Farm struct {
	pots *list.List
	rules []PropRule
}
// end::structs[]

func (f *Farm) checkPropagation() *Farm {
	rules := f.rules
	// check if left/right most pots have plants -> pad by at least 2
	item := f.pots.Front()
	frontFill := false
	for i := 0; i < 5; i++ {
		p := item.Value.(*Pot)
		if p.hasPlant {
			frontFill = true
		}
		item = item.Next()
	}
	if frontFill {
		front := f.pots.Front().Value.(*Pot)
		f.pots.PushFront(&Pot{id: front.id-1})
		f.pots.PushFront(&Pot{id: front.id-2})
		f.pots.PushFront(&Pot{id: front.id-3})
		f.pots.PushFront(&Pot{id: front.id-4})
		// fmt.Println("Added 2 items in front")
	}
	backFill := false
	item = f.pots.Back()
	for i := 0; i < 5; i++ {
		p := item.Value.(*Pot)
		if p.hasPlant {
			backFill = true
		}
		item = item.Prev()
	}
	if backFill {
		back := f.pots.Back().Value.(*Pot)
		f.pots.PushBack(&Pot{id: back.id+1})
		f.pots.PushBack(&Pot{id: back.id+2})
		f.pots.PushBack(&Pot{id: back.id+3})
		f.pots.PushBack(&Pot{id: back.id+4})
		// fmt.Println("Added 2 items in back")
	}

	for i := f.pots.Front().Next().Next(); i.Next().Next() != nil; i = i.Next() {
		p := i.Value.(*Pot)
		current := [5]bool{i.Prev().Prev().Value.(*Pot).hasPlant, i.Prev().Value.(*Pot).hasPlant, i.Value.(*Pot).hasPlant, i.Next().Value.(*Pot).hasPlant, i.Next().Next().Value.(*Pot).hasPlant}
		result := false
		for _, r := range rules {
			found := 0
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
	}
	return f
}

func (p *Pot) setPlant(has bool) *Pot {
	p.hasPlant = has
	return p
}
func (p *Pot) setFuture(willHave bool) *Pot {
	p.willHavePlant = willHave
	return p
}
func (f *Farm) propagate() *Farm {
	// calculate future plant state
	f.checkPropagation()
	// update plant state
	for i := f.pots.Front(); i.Next() != nil; i = i.Next() {
		p := i.Value.(*Pot)
		p.hasPlant = p.willHavePlant
		p.willHavePlant = false
	}

	return f
}

func (f *Farm) print() {
	for i := f.pots.Front(); i != nil; i = i.Next() {
		p := i.Value.(*Pot)
		if p.hasPlant {
			fmt.Print("#")
		} else {
			fmt.Print(".")
		}
	}
	fmt.Println("")
}

func (f *Farm) getPot(id int) *Pot {
	for e := f.pots.Front(); e != nil; e = e.Next() {
		p := e.Value.(*Pot)
		if p.id == id {
			return p
		}
	}
	return &Pot{}
}

func (f *Farm) getScore() int {
	sum := 0
	for i := f.pots.Front(); i != nil; i = i.Next() {
		p := i.Value.(*Pot)
		if p.hasPlant {
			sum += p.id
		}
	}
	return sum
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
	input := readInput("../input.txt")
	initState := strings.Split(input[0], ": ")[1]
	var farm Farm
	farm.pots = extractPots(initState)
	farm.rules = extractPropagationRules(input[2:])
	// farm.print()
	for i := 0; i < 20; i++ {
		farm.propagate()
		// farm.print()
	}
	sum := farm.getScore()
	fmt.Printf("Solution part1: %d\n", sum)

	// part 2
	farm.pots = extractPots(initState)
	scores := list.New()
	d_scores := list.New()
	iteration := 0
	for {
		s := farm.getScore()
		d_s := 0
		if iteration == 0 {
			d_s = s
		} else {
			d_s = s - scores.Back().Value.(int)
		}
		// fmt.Printf(" %d, ", s)
		ix := 0
		for e := d_scores.Front(); e != nil; e = e.Next() {
			if iteration == 1000 {
				//fmt.Printf("d_score:%d, history:%d\n", d_s, e.Value)
			}
			if e.Value == d_s {
				fmt.Printf("Found recurring d_score %d for iteration:%d at index:%d\n", d_s, iteration, ix)
			}
			ix++
		}
		if iteration > 1000 {
			break
		}
		d_scores.PushBack(d_s)
		scores.PushBack(s)
		iteration++
		farm.propagate()
	}
}

// tag::pots[]
// create a slice of Pots from a given input string (##.##...###...#...)
func extractPots(state string) *list.List {
	l := list.New()
	for i, c := range state {
		p := Pot{id: i}
		if c == '#' {
			p.hasPlant = true
		}

		l.PushBack(&p)
	}
	return l
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