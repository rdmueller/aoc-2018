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

func TestBanr(t *testing.T) {
	ops := getOpCodes()
	reg := [4]int{15, 9, 3, 6}
	op1 := [3]int{0, 1, 1}
	res := ops["banr"](reg, op1)
	if res[1] != 9 {
		t.Error("bitwise and 15&9 fails", res)
	}
	op2 := [3]int{2, 3, 1}
	res = ops["banr"](reg, op2)
	if res[1] != 2 {
		t.Error("bitwise and 15&9 fails", res)
	}
}
