package main

import (
	"testing"
)

func TestRoomExpand(t *testing.T) {
	room := NewRoom()
	var xdim, ydim int
	xdim, ydim = room.dim()
	if ydim != 3 || xdim != 3 {
		t.Error("Expected initial room to be 3x3", xdim, ydim)
	}

	room.expand(2, 0)
	xdim, ydim = room.dim()
	if ydim != 3 || xdim != 7 {
		t.Error("Room did not expand into +x as expected", xdim, ydim)
	}

	room.expand(-2, -4)
	xdim, ydim = room.dim()
	if ydim != 3+4*2 || xdim != 11 {
		t.Error("Room did not expand into -x/-y as expected", xdim, ydim)
	}

	room = NewRoom()
	room.expand(0, 1)
	xdim, ydim = room.dim()
	if ydim != 5 || xdim != 3 {
		t.Error("Room did not expand into y+1", xdim, ydim)
	}
}

func TestRoomIsWall(t *testing.T) {
	room := NewRoom()
	m := map[Position]bool{
		Position{1,1}: false,
		Position{1,2}: false,
		Position{0,0}: true,
		Position{1,1}: false,
	}
	for p, exp := range m {
		if room.isWall(p) != exp {
			t.Error("Expected isWall=", exp, "at position", p)
		}
	}
}