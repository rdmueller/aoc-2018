package main

import (
	"fmt"
	"strings"
)
type Pos struct {
	x, y int
}
func (p *Pos) isWall() bool {
	return true
}
type Room struct {
	rows []string
	origin Pos
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
			for i := 0; i < growX; i++ {
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