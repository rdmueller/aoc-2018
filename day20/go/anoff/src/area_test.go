package main

import (
	"testing"
)

func TestAreaExpand(t *testing.T) {
	area := NewArea()
	var xdim, ydim int
	xdim, ydim = area.dim()
	if ydim != 3 || xdim != 3 {
		t.Error("Expected initial area to be 3x3", xdim, ydim)
	}

	area.expand(2, 0)
	xdim, ydim = area.dim()
	if ydim != 3 || xdim != 7 {
		t.Error("Area did not expand into +x as expected", xdim, ydim)
	}

	area.expand(-2, -4)
	xdim, ydim = area.dim()
	if ydim != 3+4*2 || xdim != 11 {
		t.Error("Area did not expand into -x/-y as expected", xdim, ydim)
	}

	area = NewArea()
	area.expand(0, 1)
	xdim, ydim = area.dim()
	if ydim != 5 || xdim != 3 {
		t.Error("Area did not expand into y+1", xdim, ydim)
	}
}

func TestAreaIsWall(t *testing.T) {
	area := NewArea()
	m := map[vPosition]bool{
		vPosition{Position{1,1,}, area.expV}: false,
		vPosition{Position{1,2,}, area.expV}: false,
		vPosition{Position{0,0,}, area.expV}: true,
		vPosition{Position{1,1,}, area.expV}: false,
	}
	for p, exp := range m {
		if area.isWall(p) != exp {
			t.Error("Expected isWall=", exp, "at position", p)
		}
	}
}