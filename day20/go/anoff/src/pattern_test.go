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
	if !StringSliceEqual(exp, res) {
		t.Error("Not returning the correct paths", exp, res)
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
	if !StringSliceEqual(exp, res) {
		t.Error("Not returning the correct combinations", exp, res)
	}

	pattern2 := Pattern{[]rune("ENWWW(NEEE|SSE(EE|N))")}
	exp2 := []string{
		"ENWWWNEEE",
		"ENWWWSSEEE",
		"ENWWWSSEN",
	}
	res2, _ := expandGroup(&pattern2, 0)
	if !StringSliceEqual(exp2, res2) {
		t.Error("Not returning the correct combinations", exp2, res2)
	}
}

func testExpandGroupSkipGroup(t *testing.T) {
	pattern := Pattern{[]rune("ENNWSWW(NEWS|)SSSE")}
	exp := []string{
		"ENNWSWWSSSE",
		"ENNWSWWNEWSSSSE",
	}
	res, _ := expandGroup(&pattern, 0)
	if !StringSliceEqual(exp, res) {
		t.Error("Not returning the correct paths", exp, res)
	}
}
