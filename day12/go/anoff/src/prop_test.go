package main
import (
	"testing"
	"strings"
	"fmt"
)

func TestPotPropagationSimple(t *testing.T) {
	rules := extractPropagationRules([]string{"...## => #"})
	pots := extractPots("#..#.#..##......###...###", 0)
	if pots[0].checkPropagation(&rules).willHavePlant != false {
		t.Errorf("Wrong propagation, expected:%t", false)
	}
	if pots[15].checkPropagation(&rules).willHavePlant != true {
		t.Errorf("Wrong propagation, expected:%t", true)
	}
}
func TestPotPropagationComplex(t *testing.T) {
	input := readInput("../test.txt")
	initState := strings.Split(input[0], ": ")[1]
	pots := extractPots(initState, 0)
	rules := extractPropagationRules(input[2:])

	tables := []struct{
		ix int
		exp bool
	}{
		{0, true},
		{10, false},
		{18, true},
		{9, true},
	}
	for _, table := range tables {
		if pots[table.ix].checkPropagation(&rules).willHavePlant != table.exp {
			t.Errorf("Wrong propagation, expected:%t", table.exp)
		}
	}
}

func TestFarmProp(t *testing.T) {
	input := readInput("../test.txt")
	initState := strings.Split(input[0], ": ")[1]
	var f Farm
	padding := 10
	f.pots = extractPots(initState, padding)
	f.rules = extractPropagationRules(input[2:])
	f.propagate()
	f.print()
	f.propagate()
	f.print()
	fmt.Println("")

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
		if f.pots[table.ix + padding].hasPlant != table.exp {
			t.Errorf("Wrong propagation at ix:%d, expected:%t", table.ix, table.exp)
		}
	}
}

/*
#...#....#.....#..#..#..#.....
##..#.#..#.....#.##..#..##....
*/