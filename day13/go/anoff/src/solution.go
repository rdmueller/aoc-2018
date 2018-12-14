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

func (n *Network) hasCart(x int, y int) (bool, Cart) {
	for _, cart := range n.carts {
		if cart.x == x && cart.y == y {
			return true, cart
		}
	}
	return false, Cart{}
}
func (n *Network) print() *Network {
	for y, line := range n.lines {
		for x, c := range line {
			if hasCart, cart := n.hasCart(x, y); hasCart {
				if cart.dx == 1 {
					fmt.Print(">")
				} else if cart.dx == -1 {
					fmt.Print("<")
				} else if cart.dy == 1 {
					fmt.Print("^")
				} else if cart.dy == -1 {
					fmt.Print("v")
				}
			} else {
				fmt.Print(string(c))
			}
		}
		fmt.Println("")
	}
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
	net.print()
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
