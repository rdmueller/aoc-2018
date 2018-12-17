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
	e := Fighter{id: uuid.Must(uuid.NewV4()).String(), hp: 200, power: 3, alliance: alliance}
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
	victims := make(map[int][]*Fighter)
	minHp := 9999
	for _, o := range opponents {
		if manhattanDistance(f.pos.x, f.pos.y, o.pos.x, o.pos.y) == 1 {
			victims[o.hp] = append(victims[o.hp], o)
			if o.hp < minHp {
				minHp = o.hp
			}
		}
	}
	if len(victims) == 0 {
		return false, &Fighter{}
	}
	if len(victims[minHp]) == 0 {
		return true, victims[minHp][0]
	}
	// sort victims with lowest HP by reading dir
	minDistance := 99999 // min dist to origin
	var victim *Fighter
	for _, f := range victims[minHp] {
		d := manhattanDistance(f.pos.x, f.pos.y, 0, 0)
		if d < minDistance {
			minDistance = d
			victim = f
		}
	}
	return true, victim
}
