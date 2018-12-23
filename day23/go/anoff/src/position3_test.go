package main

import (
	"testing"
)

func TestPosition3Manhattan(t *testing.T) {
	p0 := Position3{0, 0, 0}

	m := map[Position3]int{
		Position3{1, 5, 3}: 9,
		Position3{-1, -5, 3}: 9,
		Position3{-1, -5, -3}: 9,
		Position3{-1, -50, 20}: 71,
		Position3{1, 50, -20}: 71,
		Position3{0, 0, 0}: 0,
	}
	for p, exp := range m {
		d := p0.Manhattan(p)
		if d != exp {
			t.Errorf("Wrong result, expected %d, got %d", exp, d)
		}
	}
}

func TestPosition3IsEqual(t *testing.T) {
	p1 := Position3{1, 2, 3}
	p2 := Position3{2,2,3}
	if p1.IsEqual(p2) {
		t.Error("Points are not equal", p1, p2)
	}
	p2.x--
	if !p1.IsEqual(p2) {
		t.Error("Points should be equal", p1, p2)
	}
}