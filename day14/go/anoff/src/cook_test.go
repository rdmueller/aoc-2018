package main
import (
	"testing"
	"strings"
)

func TestCook(t *testing.T) {
	board := "37"
	r1, r2 := cook(&board, 0, 1)
	if r1 != 0 || r2 != 1 {
		t.Errorf("Wrong result for next index, r1:%d, r2:%d", r1, r2)
	}
	if board != "3710" {
		t.Error("wrong board update")
	}
}

func TestCook10(t *testing.T) {
	board := "37"
	r1 := 0
	r2 := 1
	for {
		r1, r2 = cook(&board, r1, r2)
		if len(board) > 20 {
			break
		}
	}
	b10 := strings.Join(strings.Split(board, "")[9:19], "")
	if b10 != "5158916779" {
		t.Error("Invalid sequence after 9+10 cooks")
	}
}
