package main

import (
	"testing"
)

func TestGroupDamagePotential(t *testing.T) {
	g1 := NewGroup(Unit{hp:50, damage:30, attackType:"this", initiative:0, weaknesses: []string{}, immunities: []string{}}, 10)
	g2 := NewGroup(Unit{hp:50, damage:30, attackType:"this", initiative:0, weaknesses: []string{"this"}, immunities: []string{"that"}}, 10)
	g3 := NewGroup(Unit{hp:50, damage:9, attackType:"that", initiative:0, weaknesses: []string{}, immunities: []string{}}, 6)

	if g1.damagePotential(&g2) != 600 {
		t.Error("Wrong damage potential", g1, g2)
	}
	if g3.damagePotential(&g2) != 0 {
		t.Error("Wrong damage potential", g3, g2)
	}
}