package main

import (
	"testing"
)

func TestPathExpand(t *testing.T) {
	r := NewArea()
	path := NewPath("S", &r)
	endReached := path.step()
	xdim, ydim := r.dim()
	if ydim != 5 || xdim != 3 {
		t.Error("Area did not auto-expand", xdim, ydim)
	}
	if endReached {
		t.Error("Expected to reach end of path")
	}
}
