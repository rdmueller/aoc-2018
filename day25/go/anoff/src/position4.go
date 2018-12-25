package main

import (
)

type Position4 struct {
	x int
	y int
	z int
	t int
}

func (p1 *Position4) IsEqual(p2 Position4) bool {
	if p1.x == p2.x && p1.y == p2.y && p1.z == p2.z && p1.t == p2.t {
		return true
	}
	return false
}

func (p1 *Position4) Manhattan(p2 Position4) int {
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
	if p1.t < p2.t {
		dist += p2.t - p1.t
	} else {
		dist += p1.t - p2.t
	}
	return dist
}