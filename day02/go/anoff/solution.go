package main

import (
	"fmt"
	"io"
)

func main() {
	reader := getPipeReader()

	// init part 1
	doubles := 0
	triples := 0

	// init part 2
	var previousLines []string

	for {
		lineBytes, _, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		lineString := string(lineBytes)

		// part 1
		doubleCount, tripleCount := checkOccurences(lineString)
		if doubleCount > 0 {
			doubles++
		}
		if tripleCount > 0 {
			triples++
		}

		// part 2
		for _, line2 := range previousLines {
			matchDetected := checkAlmostIdentical(lineString, line2)
			if matchDetected {
				chars := overlappingChars(lineString, line2)
				fmt.Printf("Solution for part2: %s, (l1: %s, l2: %s)\n", string(chars), lineString, line2)
			}
		}
		previousLines = append(previousLines, lineString)
	}

	fmt.Printf("Solution for part1: %d, doubles:%d, triples:%d\n", doubles*triples, doubles, triples)

}

// returns doubleCount=number of characters occuring twice, tripleCount=# chars 3 times
func checkOccurences(str string) (int, int) {
	doubleCount := 0
	tripleCount := 0
	charMap := make(map[byte]int)
	for i := 0; i < len(str); i++ {
		char := str[i]
		charMap[char] += 1
	}

	for _, v := range charMap {
		if v == 2 {
			doubleCount++
		} else if v == 3 {
			tripleCount++
		}
	}

	return doubleCount, tripleCount
}

// check if only 1 character (position aware) is mismatched
func checkAlmostIdentical(line1 string, line2 string) bool {
	mismatches := 0
	for i := 0; i < len(line1); i++ {
		if line1[i] != line2[i] {
			mismatches++
			if (mismatches > 1) {
				return false
			}
		}
	}
	return true
}

// identify overlapping characters from two strings
func overlappingChars(line1 string, line2 string) []byte {
	var chars []byte
	for i := 0; i < len(line1); i++ {
		if line1[i] == line2[i] {
			chars = append(chars, line1[i])
		}
	}
	return chars
}
