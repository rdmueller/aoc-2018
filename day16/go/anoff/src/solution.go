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
type command struct {
	opCode int
	args [3]int
}
func main() {
	input := readInput("../input.txt")
	samples := extractSamples(input)
	ops := getOperations()
	part1(samples, ops)
	program := extractProgram(input)
	part2(samples, ops, program)
}

func part1(samples []sample, ops map[string]operation) {
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

func part2(samples []sample, ops map[string]operation, program []command) {
	possibleOpCodes := make(map[string][]int)
	for _, s := range samples {
		for opName, opFunc := range ops {
			res := opFunc(s.regIn, s.args)
			if res[0] == s.regOut[0] && res[1] == s.regOut[1] && res[2] == s.regOut[2] && res[3] == s.regOut[3] {
				// check if this operation name was already added
				knownOpCode := false
				for _, opCode := range possibleOpCodes[opName] {
					if opCode == s.opCode {
						knownOpCode = true
					}
				}
				if !knownOpCode {
					possibleOpCodes[opName] = append(possibleOpCodes[opName], s.opCode)
				}
			}
		}
	}
	// resolve ambiguity
	opCode2Name := make(map[int]string)
	removeOpCode := func(p map[string][]int, code int) map[string][]int {
		for name, codes := range p {
			for ix, c := range codes {
				if c == code {
					if ix +1 < len(p[name]) {
						p[name] = append(p[name][:ix], p[name][ix+1:]...)
					} else {
						p[name] = p[name][:ix]
					}
				}
			}
		}
		return p
	}
	for {
		for opName, opCodes := range possibleOpCodes {
			if len(opCodes) == 1 {
				opCode2Name[opCodes[0]] = opName
				delete(possibleOpCodes, opName)
				possibleOpCodes = removeOpCode(possibleOpCodes, opCodes[0])
			}
		}
		// check for exit criteria
		if len(possibleOpCodes) == 0 {
			fmt.Println("...done mapping", opCode2Name)
			break
		}
	}

	// execute program
	regs := [4]int{0, 0, 0, 0}
	for _, c := range program {
		f := ops[opCode2Name[c.opCode]]
		// func (regs [4]int, ops [3]int) [4]int
		regs = f(regs, c.args)
	}
	fmt.Println("Solution for part2:", regs[0])
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

func extractProgram(input []string) []command {
	var commands []command
	s := strings.Join(input, "\n")
	chunks := strings.Split(s, "\n\n\n\n")
	for _, line := range strings.Split(chunks[1], "\n") {
		strNums := strings.Split(line, " ")
		nums := StringSlice2IntSlice(strNums)
		c := command{opCode:nums[0], args:[3]int{nums[1], nums[2], nums[3]}}
		commands = append(commands, c)
	}
	return commands
}
