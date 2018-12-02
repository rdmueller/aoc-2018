package main

import (
	"fmt"
	"io"
)

func main() {
	reader := getPipeReader()
	doubles := 0
	triples := 0

	for {
		lineBytes, _, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		lineString := string(lineBytes)
		doubleCount, tripleCount := checkOccurences(lineString)
		if doubleCount > 0 {
			doubles++
		}
		if tripleCount > 0 {
			triples++
		}
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
