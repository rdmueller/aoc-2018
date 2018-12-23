package main

import (
)

type Position3 struct {
	x int
	y int
	z int
}

func (p1 *Position3) IsEqual(p2 Position3) bool {
	if p1.x == p2.x && p1.y == p2.y && p1.z == p2.z {
		return true
	}
	return false
}

func (p1 *Position3) Manhattan(p2 Position3) int {
	dist := 0
	if p1.x < p2.x {
		dist += p2.x - p1.x
	} else {
		dist += p1.x - p2.x
	}
	if p1.y < p2.y {
		dist += p2.y - p1.y
	} else {
		dist += p1.y - p2.y
	}
	if p1.z < p2.z {
		dist += p2.z - p1.z
	} else {
		dist += p1.z - p2.z
	}
	return dist
}