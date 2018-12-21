package main

import (
	"fmt"
	"strings"
)
type Area struct {
	rows []string
	origin Position		// position that the paths are taken from
}
func (r *Area) isWall(p Position) bool {
	if r.rows[p.y][p.x] == '#' {
		return true
	}
	return false
}
func (r *Area) print() {
	for _, row := range r.rows {
		fmt.Println(row)
	}
}

func (r *Area) printPosition(p Position) {
	for y, row := range r.rows {
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
	var r Area
	r.rows = append(r.rows, "#?#")
	r.rows = append(r.rows, "?X?")
	r.rows = append(r.rows, "#?#")
	r.origin = Position{1,1}
	return r
}

// grow by N rooms in either direction (use negative values to grow "up" or "left")
func (r *Area) expand(growX int, growY int) *Area {
	xrooms := (len(r.rows[0]) - 1) / 2
	if growY != 0 {
		rowWalled := "#" + strings.Repeat("?#", xrooms)
		rowArea := "?" + strings.Repeat(".?", xrooms)
		if growY < 0 {
			for i := growY; i < 0; i++ {
				r.rows = append([]string{rowWalled, rowArea}, r.rows...)
			}
			r.origin.y -= 2*growY
		} else {
			for i := 0; i < growY; i++ {
				r.rows = append(r.rows, rowArea, rowWalled)
			}
		}
	}
	if growX > 0 {
		for i, _ := range r.rows {
			var pattern string
			if i % 2 == 0 {
				pattern = "?#"
			} else {
				pattern = ".?"
			}
			r.rows[i] = r.rows[i] + strings.Repeat(pattern, growX)
		}
	}
	if growX < 0 {
		for i, _ := range r.rows {
			var pattern string
			if i % 2 == 0 {
				pattern = "#?"
			} else {
				pattern = "?."
			}
			r.rows[i] = strings.Repeat(pattern, -growX) + r.rows[i]
		}
		r.origin.x -= 2*growX
	}
	return r
}

func (r *Area) dim() (int, int) {
	xdim := len(r.rows[0])
	ydim := len(r.rows)

	return xdim, ydim
}

func (r *Area) markDoor(p Position) *Area {
	row := strings.Split(r.rows[p.y], "")
	if row[p.x] == "?" {
		if row[p.x-1] == "#" {
			row[p.x] = "-"
		} else {
			row[p.x] = "|"
		}
		r.rows[p.y] = strings.Join(row, "")
	}

	return r
}

// mark all unknown positions as walls
func (r *Area) fillWalls() *Area {
	for i, _ := range r.rows {
		r.rows[i] = strings.Replace(r.rows[i], "?", "#", -1)
	}
	return r
}

func (r *Area) getRooms() []*Position {
	var rooms []*Position
	for y, row := range r.rows {
		for x, c := range row {
			if c == '.' {
				rooms = append(rooms, &Position{x,y})
			}
		}
	}
	return rooms
}

func (r *Area) getWalkable() []*Position {
	var positions []*Position
	for y, row := range r.rows {
		for x, c := range row {
			if c == '.' || c == '|' || c == '-' || c == 'X' {
				p := Position{x,y}
				positions = append(positions, &p)
			}
		}
	}
	return positions
}