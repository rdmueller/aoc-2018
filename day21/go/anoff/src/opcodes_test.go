package main
import (
	"testing"
)

func TestMuli(t *testing.T) {
	ops := getOperations()
	reg := [6]int{2, 0, 0, 0, 0, 0}
	store8in2 := [3]int{0, 4, 2}
	res := ops["muli"](reg, store8in2)
	if res[2] != 8 {
		t.Error("Can not perform 'muli'", res)
	}
}

func TestBanr(t *testing.T) {
	ops := getOperations()
	reg := [6]int{15, 9, 3, 6, 5, 2}
	op1 := [3]int{0, 1, 1}
	res := ops["banr"](reg, op1)
	if res[1] != 9 {
		t.Error("bitwise and 15&9 fails", res)
	}
	op2 := [3]int{2, 3, 1}
	res = ops["banr"](reg, op2)
	if res[1] != 2 {
		t.Error("bitwise and 3&6 fails", res)
	}
}

func TestAddi(t *testing.T) {
	ops := getOperations()
	reg := [6]int{15, 9, 3, 6, 5, 2}
	op1 := [3]int{2, 1, 2}
	res := ops["addi"](reg, op1)
	if res[2] != 4 {
		t.Error("addi 3+1 fails", res)
	}
	op2 := [3]int{5, 4, 0}
	res = ops["addi"](reg, op2)
	if res[0] != 6 {
		t.Error("addi 2+4 fails", res)
	}
}
