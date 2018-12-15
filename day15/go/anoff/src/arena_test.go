package main
import (
	"testing"
)

func TestArenaParsing(t *testing.T) {
	input := readInput("../test.txt")
	a := newArenaFromInput(input)
	if len(a.fighters) != 9 {
		t.Error("Incorrect number of fighters in arena")
	}
	if len(a.getElves()) != 1 {
		t.Error("Incorrect number of elves in arena")
	}
	if len(a.getGoblins()) != 8 {
		t.Error("Incorrect number of goblins in arena")
	}
}

func TestArenaPointers(t *testing.T) {
	input := readInput("../test.txt")
	a := newArenaFromInput(input)
	a.getElves()[0].takeDamage(10)
	a.getElves()[0].takeDamage(30)
	for _, f := range a.fighters {
		if f.alliance == "elves" {
			if f.hp != 260 {
				t.Error("Elf#0 did not take damage correctly", f.hp)
				break
			}
		}
	}
}
