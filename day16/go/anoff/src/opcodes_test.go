package main
import (
	"testing"
)

func TestMuli(t *testing.T) {
	ops := getOpCodes()
	reg := [4]int{2, 0, 0, 0}
	store8in2 := [3]int{0, 4, 2}
	res := ops["muli"](reg, store8in2)
	if res[2] != 8 {
		t.Error("Can not perform 'muli'", res)
	}
}
