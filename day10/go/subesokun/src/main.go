package main

import (
	"bufio"
	"fmt"
	"os"
	"reflect"
	"regexp"
	"strconv"
	"strings"
)

type spot struct {
	px, py, vx, vy int
}

func (s *spot) move(dir int) {
	s.px = s.px + dir*s.vx
	s.py = s.py + dir*s.vy
}

func getField(s *spot, field string) int {
	r := reflect.ValueOf(s)
	f := reflect.Indirect(r).FieldByName(field)
	return int(f.Int())
}

func parseSpotInput(text string) spot {
	r := regexp.MustCompile(`position=<\s*(?P<px>-?\d+),\s*(?P<py>-?\d+)>\svelocity=<\s*(?P<vx>-?\d+),\s*(?P<vy>-?\d+)`)
	matches := r.FindStringSubmatch(text)
	px, _ := strconv.Atoi(matches[1])
	py, _ := strconv.Atoi(matches[2])
	vx, _ := strconv.Atoi(matches[3])
	vy, _ := strconv.Atoi(matches[4])
	return spot{px: px, py: py, vx: vx, vy: vy}
}

func minMaxSlice(spots []spot, field string) (int, int) {
	var min, max int
	for i := range spots {
		value := getField(&spots[i], field)
		if i == 0 || value < min {
			min = value
		}
		if i == 0 || value > max {
			max = value
		}
	}
	return min, max
}

func hasSpotAt(x int, y int, spots []spot) bool {
	for i := range spots {
		if spots[i].px == x && spots[i].py == y {
			return true
		}
	}
	return false
}

func tick(spots []spot, dir int) {
	for i := range spots {
		spots[i].move(dir)
	}
}

func printSpots(spots []spot) {
	minX, maxX := minMaxSlice(spots, "px")
	minY, maxY := minMaxSlice(spots, "py")
	var plot []string
	for y := minY; y <= maxY; y++ {
		for x := minX; x <= maxX; x++ {
			if hasSpotAt(x, y, spots) {
				plot = append(plot, "#")
			} else {
				plot = append(plot, " ")
			}
		}
		plot = append(plot, "\n")
	}
	fmt.Println(strings.Join(plot, ""))
}

func searchAndPrint(spots []spot) int {
	var deltaY, minY, maxY int
	currentMinDeltaY := 0
	currentSecond := 0
	for true {
		tick(spots, 1)
		currentSecond++
		minY, maxY = minMaxSlice(spots, "py")
		deltaY = maxY - minY
		if currentMinDeltaY == 0 || deltaY < currentMinDeltaY {
			currentMinDeltaY = deltaY
		} else {
			tick(spots, -1)
			printSpots(spots)
			break
		}
	}
	return currentSecond - 1
}

func main() {
	var spots []spot
	file, _ := os.Open("input.txt")
	defer file.Close()
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		spots = append(spots, parseSpotInput(scanner.Text()))
	}

	fmt.Printf("Solution to part 1:\n")
	seconds := searchAndPrint(spots)
	fmt.Printf("Solution to part 2: %v\n", seconds)
}
