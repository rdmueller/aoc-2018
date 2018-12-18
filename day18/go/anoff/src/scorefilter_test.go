package main
import (
	"testing"
)

func TestScorefilterAdd(t *testing.T) {
	s := NewScoreFilter(10)
	s.add(5)
	s.add(4)
	if s.count != 2 {
		t.Error("Did not add values correctly")
	}
	if s.list.Front().Value != 5 {
		t.Error("Leftmost value in list is wrong")
	}
	if s.list.Front().Next().Value != 4 {
		t.Error("Second value in list is wrong")
	}
	if s.list.Back().Value != 4 {
		t.Error("Rightmost value in list is wrong")
	}
}

func TestScorefilterLimit(t *testing.T) {
	s := NewScoreFilter(5)
	for i := 0; i < 10; i++ {
		s.add(i)
	}
	if s.count != 5 {
		t.Error("Filter has more elements than length", s.count)
	}
	if s.list.Back().Value != 9 {
		t.Error("Last element should be 9, is", s.list.Back().Value)
	}
	if s.list.Front().Value != 5 {
		t.Error("First element should be 5, is", s.list.Front().Value)
	}
}

func TestScorefilterContains(t *testing.T) {
	s := NewScoreFilter(20)
	seq := []int{0, 4, 6, 2, 6, 4, 0, 4, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 }
	for _, val := range seq {
		s.add(val)
	}
	if !s.contains([]int{4, 3, 4, 1, 2}) {
		t.Error("Scorefilter does not recognize containing sequence", s.list)
	}
}
