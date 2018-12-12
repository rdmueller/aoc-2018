package main
import (
	"testing"
	_"strings"
	"fmt"
)

func TestPotPropagationSimple(t *testing.T) {
	rules := extractPropagationRules([]string{"...## => #"})
	pots := extractPots("#..#.#..##......###...###")
	if pots[0].checkPropagation(&rules).willHavePlant != false {
		t.Errorf("Wrong propagation, expected:%t", false)
	}
	if pots[16].checkPropagation(&rules).willHavePlant != true {
		t.Errorf("Wrong propagation, expected:%t", true)
	}
}
/*
func testPotPropagationInput(t *testing.T) {
	input := readInput("../test.txt")
	initState := strings.Split(input[0], ": ")[1]
	pots := extractPots(initState)
	rules := extractPropagationRules(input[2:])
}
*/