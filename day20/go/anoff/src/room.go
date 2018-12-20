package main

import (
	"fmt"
	"strings"
)
// (0,0) is always top left
type Pos struct {
	x, y int
}
type Room struct {
	rows []string
	origin Pos		// position that the paths are taken from
}
func (r *Room) isWall(p Pos) bool {
	if r.rows[p.y][p.x] == '#' {
		return true
	}
	return false
}
func (r *Room) print() {
	for _, row := range r.rows {
		fmt.Println(row)
	}
}

func NewRoom() Room {
	var r Room
	r.rows = append(r.rows, "#?#")
	r.rows = append(r.rows, "?X?")
	r.rows = append(r.rows, "#?#")
	r.origin = Pos{1,1}
	return r
}

// grow by N rooms in either direction (use negative values to grow "up" or "left")
func (r *Room) expand(growX int, growY int) *Room {
	xrooms := (len(r.rows[0]) - 1) / 2
	if growY != 0 {
		rowWalled := "#" + strings.Repeat("?#", xrooms)
		rowRoom := "?" + strings.Repeat(".?", xrooms)
		if growY < 0 {
			for i := growY; i < 0; i++ {
				r.rows = append([]string{rowWalled, rowRoom}, r.rows...)
			}
			r.origin.y -= 2*growY
		} else {
			for i := 0; i < growY; i++ {
				r.rows = append(r.rows, rowRoom, rowWalled)
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

func (r *Room) dim() (int, int) {
	xdim := len(r.rows[0])
	ydim := len(r.rows)

	return xdim, ydim
}

func (r *Room) markDoor(p Pos) *Room {
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
func (r *Room) fillWalls() *Room {
	for i, _ := range r.rows {
		r.rows[i] = strings.Replace(r.rows[i], "?", "#", -1)
	}
	return r
}