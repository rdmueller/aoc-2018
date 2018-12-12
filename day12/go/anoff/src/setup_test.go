package main
import (
	"testing"
)

func TestReadInpupt(t *testing.T) {
	s := readInput("../test.txt")
	exp := 16
	if len(s) != exp {
		t.Errorf("Invalid length of lines parsed, expected:%d but got:%d", len(s), exp)
	}
}

func TestExtractPots(t *testing.T) {
	pots := extractPots(".#..###..")
	exp := []bool{false, true, false, false, true, true, true, false, false}
	if pots.Len() != len(exp) {
		t.Errorf("Incorrect number of pots, expected:%d but got:%d", len(exp), pots.Len())
	}
	ix := 0
	for i := pots.Front(); i.Next() != nil; i = i.Next() {
		p := i.Value.(Pot)
		if p.hasPlant != exp[ix] {
			t.Errorf("Incorrect hasPlant property for pot:%d, expected:%t but got:%t", i, exp[ix], p.hasPlant)
		}
		ix++
	}
}

func TestExtractPropagationRules(t *testing.T) {
	in := []string{"...## => #","..#.. => #"}
	exp := []PropRule{
		{id: 0, pattern: [5]bool{false, false, false, true, true}, result: true},
		{id: 1, pattern: [5]bool{false, false, true, false, false}, result: true},
	}
	rules := extractPropagationRules(in)
	for i, p := range rules {
		if p.id != exp[i].id {
			t.Errorf("Incorrect id, expected:%d, got:%d", exp[i].id, p.id)
		}
		if p.pattern != exp[i].pattern {
			t.Errorf("Wrong propagation pattern for input:%d, expected:%t, got:%t", i, exp[i].pattern, p.pattern)
		}
		if p.result != exp[i].result {
			t.Errorf("Wrong propagation result for input:%d, expected:%t, got:%t", i, exp[i].result, p.result)
		}
	}
}