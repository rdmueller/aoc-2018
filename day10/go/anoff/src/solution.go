package main

import (
	"fmt"
	"helpers"
	"strconv"
	"regexp"
)
type Point struct {
	x int
	y int
	dx int
	dy int
}

func main() {
	input := helpers.AggregateInputStream()
	points := generatePoints(input)
	for i := 0; i < 10; i++ {
		fmt.Println("Iteration", i)
		printPattern(points)
		moveCloud(&points)
	}
}

func generatePoints(input []string) []Point {
	var points []Point
	for _, line := range input {
		regex := *regexp.MustCompile(`position=<\s*(?P<x>[0-9-]+),\s*(?P<y>[0-9-]+)> velocity=<\s*(?P<dx>[0-9-]+),\s*(?P<dy>[0-9-]+)>`)
		res := regex.FindStringSubmatch(line)
		if len(res) == 5 {
			x, _ := strconv.Atoi(res[1])
			y, _ := strconv.Atoi(res[2])
			dx, _ := strconv.Atoi(res[3])
			dy, _ := strconv.Atoi(res[4])
			points = append(points, Point{x, y, dx, dy})
		}
	}
	return points
}

func printPattern(points []Point) {
	xmin, xmax, ymin, ymax := getPointCloudDimensions(points)
	for y := ymin; y <= ymax; y++ {
		for x := xmin; x <= xmax; x++ {
			isMatch := false
			for _, p := range points {
				if p.x == x && p.y == y {
					fmt.Print("#")
					isMatch = true
				}
			}
			if !isMatch {
				fmt.Print(".")
			}
		}
		fmt.Print("\n")
	}
}

func getPointCloudDimensions(points []Point) (int, int, int, int) {
	xmax, ymax := -100, -100
	xmin, ymin := 1000, 1000
	for _, p := range points {
		if p.x > xmax {
			xmax = p.x
		}
		if p.x < xmin {
			xmin = p.x
		}
		if p.y > ymax {
			ymax = p.y
		}
		if p.y < ymin {
			ymin = p.y
		}
	}
	return xmin, xmax, ymin, ymax
}

func moveCloud(points *[]Point) {
	pts := *points
	for i := range *points {
		pts[i].x += pts[i].dx
		pts[i].y += pts[i].dy
	}
}