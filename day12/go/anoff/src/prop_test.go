package main
import (
	"testing"
	"strings"
	_ "fmt"
)

func TestPotPropagationSimple(t *testing.T) {
	rules := extractPropagationRules([]string{"...## => #"})
	pots := extractPots("#..#.#..##......###...###")
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
	pots := extractPots(initState)
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
