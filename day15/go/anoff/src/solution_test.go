package main

import (
	"testing"
)

func TestExamples(t *testing.T) {
	tables := []struct{
		file string
		expected int
	}{
		{"../test0.txt", 27730},
		{"../test1.txt", 36334},
		{"../test2.txt", 39514},
		{"../test3.txt", 27755},
		{"../test4.txt", 28944},
		{"../test5.txt", 18740},
	}
	for _, table := range tables {
		input := readInput(table.file)
		rounds, hitpoints := part1(input)
		if rounds*hitpoints != table.expected {
			t.Error("Wrong result for", table.file, ", rounds:", rounds, ", hitpoints:", hitpoints)
		}
	}
}