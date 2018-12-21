package main

import (
	"testing"
	"strings"
)

func TestExploreArea(t *testing.T) {
	m := map[string]string{
		"../test0.txt": "../test0-out.txt",
		"../test1.txt": "../test1-out.txt",
		"../test2.txt": "../test2-out.txt",
	}
	for in, out := range m {
		input := strings.Join(readInput(in), "")
		exp := readInput(out)
		r := exploreArea(input)
		for y, line := range exp {
			for x, _ := range line {
				if r.rows[y][x] != exp[y][x] {
					t.Errorf("Wrong character at (%d,%d) expected %s, found %s", x, y, string(exp[y][x]), string(r.rows[y][x]))
				}
			}
		}
	}
}