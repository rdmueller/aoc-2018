package main

import (
	"testing"
)

func testExpandGroup(t *testing.T) {
	// (ABC|BVD|S(A|B)) -> []string{"ABC", "BCD", "SA", "SB"}
	pattern := Pattern{[]rune("ABC|BVD|S(A|B)")}
	exp := []string{
		"ABC",
		"BVD",
		"SA",
		"SB",
	}
	res, _ := expandGroup(&pattern, 0)
	if len(exp) != len(res) {
		t.Error("Mismatched number of combinations", len(exp), len(res))
	}
	var resStr []string
	for _, c := range res {
		resStr = append(resStr, c.toString())
	}
	if !StringSliceEqual(exp, resStr) {
		t.Error("Not returning the correct paths", exp, resStr)
	}
}

func TestExpandGroupTrailing(t *testing.T) {
	// (ABC|BVD|S(A|B)C)F -> []string{"ABCF", "BCDF", "SAF", "SBF"}
	pattern := Pattern{[]rune("(ABC|BVD|S(A|B)C)F")}
	exp := []string{
		"ABCF",
		"BVDF",
		"SACF",
		"SBCF",
	}
	res, _ := expandGroup(&pattern, 0)
	if len(exp) != len(res) {
		t.Error("Mismatched number of combinations", len(exp), len(res))
	}
	var resStr []string
	for _, c := range res {
		resStr = append(resStr, c.toString())
	}
	if !StringSliceEqual(exp, resStr) {
		t.Error("Not returning the correct combinations", exp, resStr)
	}

	pattern2 := Pattern{[]rune("ENWWW(NEEE|SSE(EE|N))")}
	exp2 := []string{
		"ENWWWNEEE",
		"ENWWWSSEEE",
		"ENWWWSSEN",
	}
	res2, _ := expandGroup(&pattern2, 0)
	var resStr2 []string
	for _, c := range res2 {
		resStr2 = append(resStr2, c.toString())
	}
	if !StringSliceEqual(exp, resStr2) {
		t.Error("Not returning the correct combinations", exp2, resStr2)
	}
}

func testExpandGroupSkipGroup(t *testing.T) {
	pattern := Pattern{[]rune("ENNWSWW(NEWS|)SSSE")}
	exp := []string{
		"ENNWSWWSSSE",
		"ENNWSWWNEWSSSSE",
	}
	res, _ := expandGroup(&pattern, 0)
	var resStr []string
	for _, c := range res {
		resStr = append(resStr, c.toString())
	}
	if !StringSliceEqual(exp, resStr) {
		t.Error("Not returning the correct paths", exp, resStr)
	}
}
