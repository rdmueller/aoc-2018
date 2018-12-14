package main
import (
	"testing"
)

func TestCook(t *testing.T) {
	board := []int{3, 7}
	board, r1, r2 := cook(board, 0, 1)
	if r1 != 0 || r2 != 1 {
		t.Errorf("Wrong result for next index, r1:%d, r2:%d", r1, r2)
	}
	exp := []int{3, 7, 1, 0}
	for ix := range board {
		if board[ix] != exp[ix] {
			t.Error("wrong board update")
		}
	}
}

func TestCook10(t *testing.T) {
	board := []int{3, 7}
	r1 := 0
	r2 := 1
	for {
		board, r1, r2 = cook(board, r1, r2)
		if len(board) > 20 {
			break
		}
	}
	b10 := board[9:19]
	exp := []int{5,1,5,8,9,1,6,7,7,9}
	for ix := range b10 {
		if b10[ix] != exp[ix] {
			t.Error("wrong board update")
		}
	}
}
