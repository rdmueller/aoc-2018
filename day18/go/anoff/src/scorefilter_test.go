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
	if s.contains([]int{4, 4, 4, 4}) {
		t.Error("Scorefilter recognizes unknown sequence", s.list)
	}
}

func TestScorefilterContainsBack(t *testing.T) {
	s := NewScoreFilter(6)
	seq := []int{0, 4, 6, 2, 6, 4, 0, 4, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 }
	for _, val := range seq {
		s.add(val)
	}
	if !s.containsBack([]int{5, 1, 2, 3, 4, 5}) {
		t.Error("Does not match valid sequence at list end", s.getValues())
	}
	if s.containsBack([]int{4, 4, 4, 4}) {
		t.Error("Scorefilter recognizes unknown sequence", s.getValues())
	}
	if s.containsBack([]int{2, 3, 4}) {
		t.Error("Scorefilter recognizes partial sequence that is not at the end", s.getValues())
	}
}

func TestScorefilterVals(t *testing.T) {
	s := NewScoreFilter(6)
	seq := []int{0, 4, 6, 2, 6, 4 }
	for _, v := range seq {
		s.add(v)
	}
	vals := s.getValues()
	for i, exp := range seq {
		if vals[i] != exp {
			t.Error("Value in filter not as expected", vals[i], "!=", exp)
		}
	}
	s = NewScoreFilter(20)
	s.add(3).add(5).add(7)
	vals = s.getValues()
	if len(vals) != 3 {
		t.Error("Does not return the correct number as elements")
	}
}

func TestScorefilterFindRecurringPatternOfLength(t *testing.T) {
	s := NewScoreFilter(30)
	seq := []int{0, 4, 0, 1, 6, 1, 1, 2, 3, 4, 1, 2, 3, 4, 0, 6, 3, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6}
	for _, v := range seq {
		s.add(v)
	}
	patternStartIx, pattern := s.findRecurringPatternOfLength(4)
	if patternStartIx == -1 {
		t.Error("Did not find recurring pattern of length:4")
	}
	if patternStartIx != 6 {
		t.Error("Pattern detected at wrong index, expected:", 6, ", got:", patternStartIx)
	}
	exp := []int{1,2,3,4}
	for i, _ := range pattern {
		if pattern[i] != exp[i] {
			t.Error("Found wrong pattern, expected:", exp, ", found:", pattern)
			break
		}
	}

	// find pattern at the end
	patternStartIx, pattern = s.findRecurringPatternOfLength(6)
	if patternStartIx == -1 {
		t.Error("Did not find recurring pattern of length:6 at the end of the queue")
	}
	if patternStartIx != 17 {
		t.Error("Pattern detected at wrong index, expected:", 17, ", got:", patternStartIx)
	}
	exp = []int{1,2,3,4,5,6}
	for i, _ := range pattern {
		if pattern[i] != exp[i] {
			t.Error("Found wrong pattern, expected:", exp, ", found:", pattern)
			break
		}
	}

	// handle large patterns
	s.findRecurringPatternOfLength(60)
}