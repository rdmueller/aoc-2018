package main

import (
)

type Position struct {
	x int
	y int
}

func (p1 *Position) IsEqual(p2 Position) bool {
	if p1.x == p2.x && p1.y == p2.y {
		return true
	}
	return false
}