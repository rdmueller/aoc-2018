package main
import (
	"testing"
	"strings"
)

func TestNewArea(t *testing.T) {
	input := readInput("../test.txt")
	a := NewAreaFromInput(input)
	inXmax := len(input[0]) -1
	inYmax := len(input) -1
	if a.xmax != inXmax || a.ymax != inYmax {
		t.Error("Wrong width or length compared to input")
	}
	if len(a.fields) != (a.xmax+1) * (a.ymax+1) {
		t.Error("Number of fields does not match row/column size")
	}
}

func TestStateSwitch(t *testing.T) {
	exp9 := strings.Replace(`..||###...
.||#####..
||##...##.
||#....###
|##....##|
||##..###|
||######||
|||###||||
||||||||||
||||||||||`, "\n", "", -1)
	input := readInput("../test.txt")
	a := NewAreaFromInput(input)
	for minute := 0; minute < 9; minute++ {
		a.tick()
	}
	for i, f := range a.fields {
		exp := exp9[i]
		if f.state != string(exp) {
			t.Errorf("Invalid state for (%d,%d)=%s, expected=%s", f.x, f.y, f.state, string(exp))
		}
	}
}
