package main
import (
	"testing"
)

func TestNetwork(t *testing.T) {
	net := parseInput(readInput("../test.txt"))
	if len(net.carts) != 2 {
		t.Error("Wrong number of carts extracted")
	}
	if net.carts[0].x != 2 || net.carts[0].y != 0 {
		t.Error("First cart in wrong location")
	}
	if net.carts[1].x != 9 || net.carts[1].y != 3 {
		t.Error("Second cart in wrong location")
	}
}
