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
	input := readInput("../input.txt")
	samples := extractSamples(input)
	ops := getOperations()
	moreThan2WorkingOps := 0
	for _, s := range samples {
		workingOps := 0
		for _, opFunc := range ops {
			res := opFunc(s.regIn, s.args)
			if res[0] == s.regOut[0] && res[1] == s.regOut[1] && res[2] == s.regOut[2] && res[3] == s.regOut[3] {
				workingOps++
			}
		}
		if workingOps > 2 {
			moreThan2WorkingOps++
		}
	}
	fmt.Println("Solution for part1:", moreThan2WorkingOps)
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
