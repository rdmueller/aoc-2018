package main

import (
	"container/list"
)
type scoreFilter struct {
	list list.List
	length int
	count int
}
func NewScoreFilter(length int) *scoreFilter {
	return &scoreFilter{length:length}
}
func (s *scoreFilter) add(value int) *scoreFilter {
	s.list.PushBack(value)
	s.count++
	if s.count > s.length {
		s.list.Remove(s.list.Front())
		s.count--
	}
	return s
}
func (s *scoreFilter) contains(values []int) bool {
	i := 0
	for e := s.list.Front(); e != nil; e = e.Next() {
		v := e.Value
		if values[i] == v {
			i++
		} else if values[i] != v && i > 0 {
			// reset if a partial sequence match is interrupted
			i = 0
		}
		if i == len(values) {
			return true
		}
	}
	return false
}
// check if the string is equal to the back(right, newest) of the filter
func (s *scoreFilter) containsBack(values []int) bool {
	n := s.list.Back()
	for i := len(values) - 1; i >= 0; i-- {
		if (n == nil) {
			// stop if start of list is reached
			return false
		}
		if n.Value != values[i] {
			return false
		}
		n = n.Prev()
	}
	return true
}
func (s *scoreFilter) getValues() []int {
	var vals []int
	for e := s.list.Front(); e != nil; e = e.Next() {
		vals = append(vals, e.Value.(int))
	}
	return vals
}

// -> index, pattern
func (s *scoreFilter) findRecurringPatternOfLength(l int) (int, []int) {
	var pattern []int
	vals := s.getValues()
	for i := 0; i + l < len(vals); i++ {
		if vals[i] == vals[i+l] {
			pattern = append(pattern, vals[i])
			if len(pattern) == l {
				return i-l+1, pattern
			}
		} else if len(pattern) == l {
			return i-l+1, pattern
		} else if len(pattern) > 0 {
			pattern = []int{}
		}
	}
	return -1, []int{}
}