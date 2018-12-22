package main

import (
	"fmt"
)

type Cave struct {
	depth int
	mouth Position
	target Position
	rows [][]int // contains the erosion level
}

func NewCave(depth int, target Position) Cave {
	c := Cave{depth: depth, target: target, mouth: Position{0, 0}}
	return c
}

func (c *Cave) explore() *Cave {
	c.rows = [][]int{}
	for y := 0; y <= c.target.y; y++ {
		c.rows = append(c.rows, []int{})
		for x := 0; x <= c.target.x; x++ {
			p := Position{x,y}
			geo := c.getGeoIndex(p)
			erosion := (geo + c.depth) % 20183
			c.rows[y] = append(c.rows[y], erosion)
		}
	}

	return c
}

func (c *Cave) getGeoIndex(p Position) int {
	if p.IsEqual(c.mouth) {
		return 0
	} else if p.IsEqual(c.target) {
		return 0
	} else if p.y == 0 {
		return p.x * 16807
	} else if p.x == 0 {
		return p.y * 48271
	} else {
		p1 := p
		p1.x--
		p2 := p
		p2.y--
		return c.getErosionLevel(p1) * c.getErosionLevel(p2)
	}
}
func (c *Cave) getErosionLevel(p Position) int {
	return c.rows[p.y][p.x]
}

func (c *Cave) riskScore() int {
	score := 0
	for _, row := range c.rows {
		for _, erosion := range row {
			score += erosion % 3
		}
	}
	return score
}

func (c *Cave) print() {
	for y, row := range c.rows {
		for x, erosion := range row {
			if x == c.mouth.x && y == c.mouth.y {
				fmt.Print("M")
			} else if x == c.target.x && y == c.target.y {
				fmt.Print("T")
			} else {
				switch erosion % 3 {
					case 0:
						fmt.Print(".")
					case 1:
						fmt.Print("=")
					case 2:
						fmt.Print("|")
					default:
						panic("This should not happen")
				}
			}
		}
		fmt.Println("")
	}
}