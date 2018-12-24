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

func (g *Group) damagePotential(t *Group) int {
	d := g.getPower()
	for _, immune := range t.immunities {
		if g.attackType == immune {
			d = 0
		}
	}
	for _, weak := range t.weaknesses {
		if g.attackType == weak {
			d *= 2
		}
	}
	return d
}