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
	hasMoved bool
}
type Network struct {
	lines []string
	carts []Cart
}
// end::structs[]

func (c *Cart) turnRight() *Cart {
	if c.dx == 1 {
		c.dx = 0
		c.dy = 1
	} else if c.dx == -1 {
		c.dx = 0
		c.dy = -1
	} else if c.dy == 1 {
		c.dx = -1
		c.dy = 0
	} else if c.dy == -1 {
		c.dx = 1
		c.dy = 0
	} else {
		panic("Cart is moving out of range")
	}
	return c
}
func (c *Cart) turnLeft() *Cart {
	if c.dx == 1 {
		c.dx = 0
		c.dy = -1
	} else if c.dx == -1 {
		c.dx = 0
		c.dy = 1
	} else if c.dy == 1 {
		c.dx = 1
		c.dy = 0
	} else if c.dy == -1 {
		c.dx = -1
		c.dy = 0
	} else {
		panic("Cart is moving out of range")
	}
	return c
}
func (c *Cart) move() *Cart {
	c.x += c.dx
	c.y += c.dy
	c.hasMoved = true
	return c
}
func (n *Network) addCart(c Cart) *Network {
	n.carts = append(n.carts, c)
	return n
}
func (n *Network) getCarts() *[]Cart {
	return &n.carts
}
func (n *Network) moveCarts() *Network {
	for y, line := range n.lines {
		for x, _ := range line {
			if hasCart, cart := n.hasCart(x, y); hasCart {
				// check if cart has already been moved (might be moved into future for loop)
				if cart.hasMoved {
					continue
				}
				cart.move()
					rail := n.lines[cart.y][cart.x]
				switch rail {
				case '+':
					switch cart.lastTurn {
					case "left":
						// keep moving
						cart.lastTurn = "straight"
					case "straight":
						cart.turnRight()
						cart.lastTurn = "right"
					default: // start and last="right"
						cart.turnLeft()
						cart.lastTurn = "left"
					}
				case '/':
					if cart.dy == -1 {
						cart.turnRight()
					} else {
						cart.turnLeft()
					}
				case '\\':
					if cart.dy == -1 {
						cart.turnLeft()
					} else {
						cart.turnRight()
					}
				case '-':
					// keep moving
				case '|':
					// keep moving
				default:
					panic("Unexpected rail type")
				}
			}
		}
	}
	for i := range n.carts {
		n.carts[i].hasMoved = false
	}
	return n
}

func (n *Network) hasCart(x int, y int) (bool, *Cart) {
	for i := range n.carts {
		if n.carts[i].x == x && n.carts[i].y == y {
			return true, &n.carts[i]
		}
	}
	return false, &Cart{}
}
func (n *Network) print() *Network {
	for y, line := range n.lines {
		for x, c := range line {
			if hasCart, cart := n.hasCart(x, y); hasCart {
				if cart.dx == 1 {
					fmt.Print(">")
				} else if cart.dx == -1 {
					fmt.Print("<")
				} else if cart.dy == -1 {
					fmt.Print("^")
				} else if cart.dy == 1 {
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

// create a Network struct from input string
func parseInput(input []string) Network {
	var net Network
	for y, line := range input {
		for x, c := range line {
			if c == '^' {
				c := Cart{x:x, y:y, dy:-1, dx: 0}
				net.addCart(c)
			} else if c == 'v' {
				c := Cart{x:x, y:y, dy:1, dx: 0}
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
	for i := 0; i < 3; i++ {
		fmt.Printf("\n\nStep %d\n", i)
		net.moveCarts()
		net.print()
	}
}

func readInput(filepath string) []string {
	b, err := ioutil.ReadFile(filepath)
	if err != nil {
			panic(err)
	}

	s := string(b)
	return strings.Split(s, "\n")
}
