package main

import (
	"sort"
	_"fmt"
)

type Army struct {
	groups []*Group
	faction string
}

func NewArmy(faction string) Army {
	a := Army{faction:faction}
	return a
}

func (a *Army) addGroup(g *Group) *Army {
	g.faction = a.faction
	g.id = len(a.groups)+1
	a.groups = append(a.groups, g)
	return a
}

func (a *Army) sortGroupsByPower() *Army {
	sort.Sort(byPower(a.groups))
	return a
}

type byPower []*Group
func (g byPower) Len() int {
	return len(g)
}
func (g byPower) Swap(i, j int) {
	g[i], g[j] = g[j], g[i]
}
func (g byPower) Less(i, j int) bool {
	if g[i].getPower() > g[j].getPower() {
		return true
	} else if g[i].getPower() == g[j].getPower() {
		if g[i].initiative > g[j].initiative {
			return true
		}
		return false
	}
	return false
}

func (a *Army) planAttack(t *Army) *Army {
	a.sortGroupsByPower()
	for _, group := range a.groups {
		maxDamage := 0
		var maxDamageGroup *Group
		for _, tgroup := range t.groups {
			if !a.isTargetingGroup(tgroup) && group.damagePotential(tgroup) > maxDamage && tgroup.units > 0 {
				maxDamage = group.damagePotential(tgroup)
				maxDamageGroup = tgroup
			}
		}
		// TODO: handle ties
		// If an attacking group is considering two defending groups to which it would deal equal damage, it chooses to target the defending group with the largest effective power; if there is still a tie, it chooses the defending group with the highest initiative.
		if maxDamageGroup != nil {
			group.target = maxDamageGroup
		} else {
			group.target = nil
			// fmt.Println("Could not find a target for group", group)
		}
	}
	return a
}

func (a *Army) isTargetingGroup(t *Group) bool {
	for _, g := range a.groups {
		if g.target == t {
			return true
		}
	}
	return false
}

func (a *Army) cleanup() *Army {
	for i, g := range a.groups {
		if g.units <= 0 {
			if i == 0 || i == len(a.groups) {
				panic("waa")
			}
			a.groups = append(a.groups[:i], a.groups[i+1:]...)
		}
		g.target = nil
	}

	return a
}