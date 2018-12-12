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
	pots := extractPots(".#..###..", 0)
	exp := []bool{false, true, false, false, true, true, true, false, false}
	if len(pots) != len(exp) {
		t.Errorf("Incorrect number of pots, expected:%d but got:%d", len(exp), len(pots))
	}
	for i, p := range pots {
		if p.hasPlant != exp[i] {
			t.Errorf("Incorrect hasPlant property for pot:%d, expected:%t but got:%t", i, exp[i], p.hasPlant)
		}
	}

	pots = extractPots(".#.", 5)
	if len(pots) != 3+2*5 {
		t.Errorf("Padding incorrect, got length:%d", len(pots))
	}
	if pots[0].id != -5 {
		t.Errorf("Wrong Pot ID for padded elements, got:%d", pots[0].id)
	}
}

func TestExtractPotsLinks(t *testing.T) {
	pots := extractPots(".#..###..", 0)
	if pots[0].left.id != -1 {
		t.Error("No dummy link found on left border")
	}
	if pots[0].left.left.id != -1 {
		t.Error("No propagation in dummy link")
	}
	if pots[1].right.id != pots[2].id {
		t.Errorf("Invalid link between pot %d and %d", 1, 2)
	}
	if pots[1].right.right.id != pots[3].id {
		t.Errorf("Invalid link between pot %d and %d", 1, 3)
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