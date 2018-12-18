package main
import (
	"testing"
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
