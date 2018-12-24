package main

import (
	"sort"
	"fmt"
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
			if !a.isTargetingGroup(tgroup) && group.damagePotential(tgroup) > maxDamage {
				maxDamage = group.damagePotential(tgroup)
				maxDamageGroup = tgroup
			}
		}
		if maxDamageGroup != nil {
			group.target = maxDamageGroup
		} else {
			group.target = nil
			fmt.Println("Could not find a target for group", group)
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