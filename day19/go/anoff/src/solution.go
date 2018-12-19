package main

import (
	"fmt"
	"strings"
)

type command struct {
	op string
	args [3]int
}
type program struct {
	commands []*command
}
func (p *program) instructions() {
	for _, c := range p.commands {
		fmt.Println(c)
	}
}
func main() {
	input := readInput("../test.txt")
	//ops := getOperations()
	p := NewProgram(input)
	p.instructions()
}


func NewProgram(input []string) program {
	var commands []*command
	for _, line := range input {
		str := strings.Split(line, " ")
		nums := StringSlice2IntSlice(str[1:])
		if str[0] == "#ip" {
			commands = append(commands, &command{op:str[0], args:[3]int{nums[0], -1, -1}})
		} else {
			c := command{op:str[0], args:[3]int{nums[0], nums[1], nums[2]}}
			commands = append(commands, &c)
		}
	}
	return program{commands: commands}
}
