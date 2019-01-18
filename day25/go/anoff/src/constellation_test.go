package main

import (
	"testing"
)

func TestConstellationAppend(t *testing.T) {
	c0 := Constellation{Position4{0, 0, 0, 0}}
	c0 = append(c0, Position4{1,1,1,1})
	if len(c0) != 2 {
		t.Error("Expected constellation to contain two points")
	}
}

func TestConstellationIsPart(t *testing.T) {
	c0 := Constellation{Position4{0, 0, 0, 0}}
	p1 := Position4{0, 1, 0, 1}
	p2 := Position4{0, 1, 3, 1}
	if !p1.isPart(c0) {
		t.Error("Point should be part of constellation", p1)
	}
	if p2.isPart(c0) {
		t.Error("Point should NOT be part of constellation", p2)
	}
}