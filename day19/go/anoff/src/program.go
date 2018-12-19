package main

import (
	"fmt"
	"strings"
)
// tag::program[]
type command struct {
	op string
	args [3]int
}
type program struct {
	commands []*command
	pointerRegister int
	registers [6]int
	operations map[string]operation
}
// end::program[]
func (p *program) instructions() {
	for _, c := range p.commands {
		fmt.Println(c)
	}
}

func (p *program) print() {
	fmt.Println("Pointer stored in register", p.pointerRegister, ", value:", p.registers[p.pointerRegister])
	fmt.Println("Registers:", p.registers)
	p.instructions()
}

// -> true if end of program reached
func (p *program) step(debug bool) bool {
	pointer := p.registers[p.pointerRegister]
	cmd := p.commands[pointer]
	op := p.operations[cmd.op]
	out := op(p.registers, cmd.args)
	if debug == true {
		fmt.Printf("ip=%d %d %s(%d) %d\n", pointer, p.registers, cmd.op, cmd.args, out)
	}
	p.registers = out
	p.registers[p.pointerRegister]++
	if p.registers[p.pointerRegister] > len(p.commands) -1 {
		return true
	}
	return false
}

func NewProgram(input []string) program {
	p := program{operations: getOperations()}
	for _, line := range input {
		str := strings.Split(line, " ")
		nums := StringSlice2IntSlice(str[1:])
		if str[0] == "#ip" {
			p.pointerRegister = nums[0]
		} else {
			c := command{op:str[0], args:[3]int{nums[0], nums[1], nums[2]}}
			p.commands = append(p.commands, &c)
		}
	}
	return p
}
