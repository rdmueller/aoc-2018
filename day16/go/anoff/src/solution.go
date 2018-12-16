package main

import (
	"fmt"
	"strconv"
	"strings"
)
type sample struct {
	regIn [4]int
	opCode int
	args [3]int
	regOut [4]int
}
func main() {
	input := readInput("../test.txt")
	samples := extractSamples(input)
	fmt.Println(samples)
}

func extractSamples(input []string) []sample {
	var samples []sample
	var s sample
	previousStep := ""
	for _, line := range input {
		if strings.Contains(line, "Before:") {
			s = sample{}
			str := strings.Split(line, "[")[1]
			str = strings.Split(str, "]")[0]
			strNums := strings.Split(str, ", ")
			if len(strNums) != 4 {
				panic("Should have 4 input registers")
			}
			for i, strNum := range strNums {
				num, _ := strconv.Atoi(strNum)
				s.regIn[i] = num
			}
			previousStep = "before"
		} else if strings.Contains(line, "After:") {
			str := strings.Split(line, "[")[1]
			str = strings.Split(str, "]")[0]
			strNums := strings.Split(str, ", ")
			if len(strNums) != 4 {
				panic("Should have 4 output registers")
			}
			for i, strNum := range strNums {
				num, _ := strconv.Atoi(strNum)
				s.regOut[i] = num
			}
			previousStep = "after"
			samples = append(samples, s)
		} else if previousStep == "before" {
			strNums := strings.Split(line, " ")
			if len(strNums) != 4 {
				panic("Should have 4 ops")
			}
			num, _ := strconv.Atoi(strNums[0])
			s.opCode = num
			for i, strNum := range strNums[1:] {
				num, _ := strconv.Atoi(strNum)
				s.args[i] = num
			}
		}
	}
	return samples
}
