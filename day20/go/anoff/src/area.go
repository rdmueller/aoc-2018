package main

import (
	"fmt"
	"strings"
	"container/list"
)
type Area struct {
	rows []string
	origin vPosition		// position that the paths are taken from
	expansions *list.List
	expV int // expansion version aka number of expansions
}
func (a *Area) isWall(p vPosition) bool {
	if a.rows[p.y][p.x] == '#' {
		return true
	}
	return false
}
func (a *Area) print() {
	for _, row := range a.rows {
		fmt.Println(row)
	}
}

func (a *Area) printPosition(p vPosition) {
	for y, row := range a.rows {
		for x, c := range row {
			if x == p.x && y == p.y {
				fmt.Print("0")
			} else {
				fmt.Print(string(c))
			}
		}
		fmt.Println("")
	}
}

func NewArea() Area {
	var a Area
	a.expV = 0
	a.rows = append(a.rows, "#?#")
	a.rows = append(a.rows, "?X?")
	a.rows = append(a.rows, "#?#")
	a.origin = vPosition{Position{1, 1}, a.expV}
	a.expansions = list.New()
	return a
}

// grow by N rooms in either direction (use negative values to grow "up" or "left")
func (a *Area) expand(growX int, growY int) *Area {
	xrooms := (len(a.rows[0]) - 1) / 2
	if growY != 0 {
		rowWalled := "#" + strings.Repeat("?#", xrooms)
		rowArea := "?" + strings.Repeat(".?", xrooms)
		if growY < 0 {
			for i := growY; i < 0; i++ {
				a.rows = append([]string{rowWalled, rowArea}, a.rows...)
			}
		} else {
			for i := 0; i < growY; i++ {
				a.rows = append(a.rows, rowArea, rowWalled)
			}
		}
	}
	if growX > 0 {
		for i, _ := range a.rows {
			var pattern string
			if i % 2 == 0 {
				pattern = "?#"
			} else {
				pattern = ".?"
			}
			a.rows[i] = a.rows[i] + strings.Repeat(pattern, growX)
		}
	}
	if growX < 0 {
		for i, _ := range a.rows {
			var pattern string
			if i % 2 == 0 {
				pattern = "#?"
			} else {
				pattern = "?."
			}
			a.rows[i] = strings.Repeat(pattern, -growX) + a.rows[i]
		}
	}
	a.expV++
	a.expansions.PushBack(struct{x, y int}{growX, growY})
	a.alignPosition(&a.origin)
	return a
}
func (a *Area) alignPosition(p *vPosition) {
	if p.version != a.expV {
		i := 0
		for n := a.expansions.Front(); n != nil; n = n.Next() {
			if i == p.version {
				// fmt.Println("updating version..", a.expV, p)
				grow := n.Value.(struct{x, y int})
				if grow.x < 0 {
					p.x -= 2*grow.x
				}
				if grow.y < 0 {
					p.y -= 2*grow.y
				}
				p.version++
			}
			i++
		}
	}
}
func (a *Area) dim() (int, int) {
	xdim := len(a.rows[0])
	ydim := len(a.rows)

	return xdim, ydim
}

func (a *Area) markDoor(p vPosition) *Area {
	row := strings.Split(a.rows[p.y], "")
	if row[p.x] == "?" {
		if row[p.x-1] == "#" {
			row[p.x] = "-"
		} else {
			row[p.x] = "|"
		}
		a.rows[p.y] = strings.Join(row, "")
	}

	return a
}

// mark all unknown positions as walls
func (a *Area) fillWalls() *Area {
	for i, _ := range a.rows {
		a.rows[i] = strings.Replace(a.rows[i], "?", "#", -1)
	}
	return a
}

func (a *Area) getRooms() []*Position {
	var rooms []*Position
	for y, row := range a.rows {
		for x, c := range row {
			if c == '.' {
				rooms = append(rooms, &Position{x,y})
			}
		}
	}
	return rooms
}

func (a *Area) getWalkable() []*Position {
	var positions []*Position
	for y, row := range a.rows {
		for x, c := range row {
			if c == '.' || c == '|' || c == '-' || c == 'X' {
				p := Position{x,y}
				positions = append(positions, &p)
			}
		}
	}
	return positions
}