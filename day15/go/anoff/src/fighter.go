package main

import (
	"github.com/satori/go.uuid"
)

type Fighter struct {
	id string
	pos Position
	hp int
	power int
	alliance string
	tookTurn bool
}

func NewFighter(alliance string) Fighter {
	e := Fighter{id: uuid.Must(uuid.NewV4()).String(), hp: 300, power: 3, alliance: alliance}
	return e
}

func (f *Fighter) move(dest Position) *Fighter {
	f.pos.x = dest.x
	f.pos.y = dest.y
	return f
}

func (f *Fighter) takeDamage(power int) *Fighter {
	f.hp -= power
	return f
}

func (f *Fighter) inAttackDistance(opponents []*Fighter) (bool, *Fighter) {
	for _, o := range opponents {
		// TODO: handle multiple matches, top-down prio
		if manhattanDistance(f.pos.x, f.pos.y, o.pos.x, o.pos.y) == 1 {
			return true, o
		}
	}
	return false, &Fighter{}
}
