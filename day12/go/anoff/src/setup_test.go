package main
import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestReadInpupt(t *testing.T) {
	assert.Equal(t, 16, len(readInput("../test.txt")))
}

func TestExtractPots(t *testing.T) {
	p := extractPots(".#..###..")
	exp := []bool{false, true, false, false, true, true, true, false, false}
	if len(p) != len(exp) {
		t.Errorf("Incorrect number of pots, expected:%d but got:%d", len(exp), len(p))
	}
	for i, p := range p {
		if p.hasPlant != exp[i] {
			t.Errorf("Incorrect hasPlant property for pot:%d, expected:%t but got:%t", i, exp[i], p.hasPlant)
		}
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