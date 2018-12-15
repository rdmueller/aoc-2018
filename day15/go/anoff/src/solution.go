package main

import (
	"github.com/satori/go.uuid"
)

type Fighter struct {
	id string
	x int
	y int
	hp int
	power int
}

func NewFighter() Fighter {
	e := Fighter{id: uuid.Must(uuid.NewV4()).String(), hp: 300, power: 3}
	return e
}
