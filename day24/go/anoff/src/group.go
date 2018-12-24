package main

import (

)

type Group struct {
	Unit
	units int		// number of units
}

func (g *Group) getPower() int {
	return g.units * g.damage
}

func NewGroup(u Unit, units int) Group {
	g := Group{u, units}
	return g
}