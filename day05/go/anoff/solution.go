package main

import (
	"fmt"
	"strings"
)

func main() {
	input := getInput(false)
	chain := strings.Join(input, "")

	// Part 1
	imploded := implodeString(chain)
	fmt.Printf("Solution for part 1: %d, (%s)\n", len(imploded), imploded)

	// Part 2
	chars := "abcdefghijklmnopqrstuvwxyz"
	minLength := 500000
	bestChar := "a"
	for _, c := range chars {
		char := string(c)
		strippedChain := strings.Replace(strings.Replace(chain, char, "", -1), strings.ToUpper(char), "", -1)
		imploded := implodeString(strippedChain)
		if len(imploded) < minLength {
			bestChar = char
			minLength = len(imploded)
		}
	}
	fmt.Printf("Solution for part 2: %d, (for char %s)\n", minLength, bestChar)
}

// go over the string and remove same characters with inverse capitalization
func removeCaseNeighbors(input string) string {
	chars := "abcdefghijklmnopqrstuvwxyz"
	
	for _, c := range chars {
		upper := strings.ToUpper(string(c))
		lower := string(c)
		input = strings.Replace(strings.Replace(input, upper+lower, "", -1), lower+upper, "", -1)
	}
	return input
}

// recursively remove neighbors until no change occurs
func implodeString(input string) string {
	deduped := removeCaseNeighbors(input)
	if len(deduped) == len(input) {
		return input
		} else {
		// fmt.Printf(".. removed %d characters\n", len(input) - len(deduped))
		return implodeString(deduped)
	}
}
