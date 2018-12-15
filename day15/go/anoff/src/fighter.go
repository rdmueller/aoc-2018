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