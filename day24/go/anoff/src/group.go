package main

import (
	"sort"
)

type Group struct {
	Unit
	units int		// number of units
	target *Group
	faction string
	id int
}

func (g *Group) getPower() int {
	return g.units * g.damage
}

func NewGroup(u Unit, units int) Group {
	g := Group{Unit:u, units:units}
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

// descending
func sortGroupsByInitiative(g []*Group) []*Group {
	sort.Sort(byInitiative(g))
	return g
}

type byInitiative []*Group
func (g byInitiative) Len() int {
	return len(g)
}
func (g byInitiative) Swap(i, j int) {
	g[i], g[j] = g[j], g[i]
}
func (g byInitiative) Less(i, j int) bool {
	if g[i].initiative > g[j].initiative {
		return true
	}
	return false
}
