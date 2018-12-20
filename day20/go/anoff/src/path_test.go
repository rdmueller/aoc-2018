package main

import (
	"testing"
)

func TestPathExpand(t *testing.T) {
	r := NewRoom()
	path := NewPath("S", &r)
	endReached := path.step()
	xdim, ydim := r.dim()
	if ydim != 5 || xdim != 3 {
		t.Error("Room did not auto-expand", xdim, ydim)
	}
	if endReached {
		t.Error("Expected to reach end of path")
	}
}