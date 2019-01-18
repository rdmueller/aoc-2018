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
	// In decreasing order of effective power, groups choose their targets; in a tie, the group with the higher initiative chooses first.
	a.sortGroupsByPower()
	for _, group := range a.groups {
		maxDamage := 0
		var targetGroup *Group
		var maxDamageGroups []*Group
		for _, tgroup := range t.groups {
			if !a.isTargetingGroup(tgroup) && tgroup.units > 0 {
				damage := group.damagePotential(tgroup)
				if damage > maxDamage {
					maxDamage = damage
					maxDamageGroups = []*Group{tgroup}
				} else if damage == maxDamage {
					maxDamageGroups = append(maxDamageGroups, tgroup)
				}
			} 
		}
		var maxPowerGroups []*Group
		if len(maxDamageGroups) > 1 {
			maxPower := 0
			for _, tgroup := range maxDamageGroups {
				if tgroup.getPower() > maxPower {
					maxPower = tgroup.getPower()
					maxPowerGroups = []*Group{tgroup}
				} else if tgroup.getPower() == maxPower {
					maxPowerGroups = append(maxPowerGroups, tgroup)
				}
			}
		} else if len(maxDamageGroups) == 1 {
			targetGroup = maxDamageGroups[0]
		}
		if len(maxPowerGroups) > 1 {
			maxInitiative := 0
			for _, tgroup := range maxPowerGroups {
				if tgroup.initiative > maxInitiative {
					maxInitiative = tgroup.initiative
					targetGroup = tgroup
				}
			}
		} else if len(maxPowerGroups) == 1 {
			targetGroup = maxPowerGroups[0]
		}
		// If an attacking group is considering two defending groups to which it would deal equal damage, it chooses to target the defending group with the largest effective power; if there is still a tie, it chooses the defending group with the highest initiative.
		if targetGroup != nil && group.damagePotential(targetGroup) > 0 {
			group.target = targetGroup
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
	for i := 0; i < len(a.groups); i++ {
		g := a.groups[i]
		if g.units <= 0 {
			if i == len(a.groups) -1 {
				a.groups = a.groups[:i]
			} else {
				a.groups = append(a.groups[:i], a.groups[i+1:]...)
				i--
			}
		}
		g.target = nil
	}

	return a
}