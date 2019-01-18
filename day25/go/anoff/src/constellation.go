package main

import (

)

type Constellation []Position4

func (p *Position4) isPart(c Constellation) bool {
	for _, point := range c {
		if p.Manhattan(point) <= 3 {
			return true
		}
	}
	return false
}

func (c Constellation) intersects(ct Constellation) bool {
	for _, p := range c {
		if p.isPart(ct) {
			return true
		}
	}
	return false
}