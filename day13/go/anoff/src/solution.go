package main

import (
	"fmt"
	"io/ioutil"
	"strings"
)

// tag::structs[]
type Cart struct {
	x int
	y int
	dx int
	dy int
	lastTurn string
}
type Network struct {
	lines []string
	carts []Cart
}
// end::structs[]

func (n *Network) addCart(c Cart) *Network {
	n.carts = append(n.carts, c)
	return n
}

func (n *Network) moveCarts() *Network {
	return n
}

// return 
func parseInput(input []string) Network {
	var net Network
	for y, line := range input {
		for x, c := range line {
			if c == '^' {
				c := Cart{x:x, y:y, dy:1, dx: 0}
				net.addCart(c)
			} else if c == 'v' {
				c := Cart{x:x, y:y, dy:-1, dx: 0}
				net.addCart(c)
			}  else if c == '<' {
				c := Cart{x:x, y:y, dy:0, dx: -1}
				net.addCart(c)
			}  else if c == '>' {
				c := Cart{x:x, y:y, dy:0, dx: 1}
				net.addCart(c)
			}
		}
		line = strings.Replace(line, "^", "|", -1)
		line = strings.Replace(line, "v", "|", -1)
		line = strings.Replace(line, "<", "-", -1)
		line = strings.Replace(line, ">", "-", -1)
		net.lines = append(net.lines, line)
	}
	return net
}
func main() {
	input := readInput("../test.txt")
	net := parseInput(input)
	for _, line := range net.lines {
		fmt.Println(line)
	}
	fmt.Println(len(net.carts))
}

func readInput(filepath string) []string {
	b, err := ioutil.ReadFile(filepath)
	if err != nil {
			panic(err)
	}

	s := string(b)
	return strings.Split(s, "\n")
}
