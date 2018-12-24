package main

import (

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
