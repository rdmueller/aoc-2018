package main
import (
	"testing"
	"strings"
	"fmt"
)

func TestPotPropagationSimple(t *testing.T) {
	rules := extractPropagationRules([]string{"...## => #"})
	pots := extractPots("#..#.#..##......###...###")
	f := Farm{pots: pots, rules: rules}
	f.checkPropagation()
	p0 := f.getPot(0)
	if p0.willHavePlant != false {
		t.Errorf("Wrong propagation, expected:%t", false)
	}
	p15 := f.getPot(15)
	if p15.willHavePlant != true {
		fmt.Println(p15)
		t.Errorf("Wrong propagation, expected:%t", true)
	}
}

func TestPotPropagationComplex(t *testing.T) {
	input := readInput("../test.txt")
	initState := strings.Split(input[0], ": ")[1]
	pots := extractPots(initState)
	rules := extractPropagationRules(input[2:])
	f := Farm{pots: pots, rules: rules}

	tables := []struct{
		ix int
		exp bool
	}{
		{0, true},
		{10, false},
		{18, true},
		{9, true},
	}
	f.checkPropagation()
	for _, table := range tables {
		p := f.getPot(table.ix)
		if p.willHavePlant != table.exp {
			t.Errorf("Wrong propagation, expected:%t", table.exp)
		}
	}
}

func TestFarmProp(t *testing.T) {
	input := readInput("../test.txt")
	initState := strings.Split(input[0], ": ")[1]
	var f Farm
	f.pots = extractPots(initState)
	f.rules = extractPropagationRules(input[2:])
	f.propagate()
	f.propagate()

	tables := []struct{
		ix int
		exp bool
	}{
		{0, true},
		{1, true},
		{4, true},
		{5, true},
		{6, false},
		{7, false},
		{8, false},
		{9, true},
		{10, true},
		{11, false},
	}
	for _, table := range tables {
		p := f.getPot(table.ix)
		if p.hasPlant != table.exp {
			t.Errorf("Wrong propagation at ix:%d, expected:%t", table.ix, table.exp)
		}
	}
}

/*
#...#....#.....#..#..#..#.....
##..#.#..#.....#.##..#..##....
*/