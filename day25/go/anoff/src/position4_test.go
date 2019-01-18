package main

import (
	"testing"
)

func TestPosition4Manhattan(t *testing.T) {
	p0 := Position4{0, 0, 0, 0}

	m := map[Position4]int{
		Position4{1, 5, 3, 0}: 9,
		Position4{-1, 0, -5, 3}: 9,
		Position4{-1, -5, 0, -3}: 9,
		Position4{0, -1, -50, 20}: 71,
		Position4{1, 50, 0, -20}: 71,
		Position4{0, 0, 0, 0}: 0,
	}
	for p, exp := range m {
		d := p0.Manhattan(p)
		if d != exp {
			t.Errorf("Wrong result, expected %d, got %d", exp, d)
		}
	}
}

func TestPosition4IsEqual(t *testing.T) {
	p1 := Position4{1, 2, 3, 3}
	p2 := Position4{2, 2, 3, 3}
	if p1.IsEqual(p2) {
		t.Error("Points are not equal", p1, p2)
	}
	p2.x--
	if !p1.IsEqual(p2) {
		t.Error("Points should be equal", p1, p2)
	}
}