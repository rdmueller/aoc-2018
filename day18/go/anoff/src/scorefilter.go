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