package main

import (
	"fmt"
	"strings"
)

func main() {
	input := getInput(false)
	chain := string(input[0])
	imploded := implodeString(chain)
	fmt.Printf("Solution for part 1: %d, (%s)\n", len(imploded), imploded)
}

// go over the string and remove same characters with inverse capitalization
func removeCaseNeighbors(input string) string {
	var deduped string
	removedLastChar := false
	for i := 1; i < len(input); i++ {
		a := string(input[i-1])
		b := string(input[i])
		if a != b && strings.ToLower(a) == strings.ToLower(b) {
			// fmt.Println("Imploding..", a, b, deduped)
			i++
			removedLastChar = true
		} else {
			deduped += a
			removedLastChar = false
			// fmt.Println("diff", a, b, deduped)
		}
	}
	if !removedLastChar {
		deduped += string(input[len(input)-1])
	}
	return string(deduped)
}

// recursively remove neighbors until no change occurs
func implodeString(input string) string {
	deduped := removeCaseNeighbors(input)
	if len(deduped) == len(input) {
		return input
		} else {
		fmt.Printf(".. removed %d characters\n", len(input) - len(deduped))
		return implodeString(deduped)
	}
}
