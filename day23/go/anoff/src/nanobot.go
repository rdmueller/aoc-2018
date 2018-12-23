package main

import (
	"regexp"
)

type Nanobot struct {
	pos Position3
	sigStrength int
}

func (n *Nanobot) InRange(p Position3) bool {
	dist := n.pos.Manhattan(p)
	if dist <= n.sigStrength {
		return true
	}
	return false
}

func NewNanobot(pos Position3, sigStrength int) Nanobot {
	n := Nanobot{pos:pos, sigStrength:sigStrength}
	return n
}

// pos=<84758878,-20128592,53214040>, r=93447130
func input2Nanobot(line string) Nanobot {
	re := regexp.MustCompile(`(?m)pos=<([-0-9]+),([-0-9]+),([-0-9]+)>,\s+r=([-0-9]+)`)
	m := re.FindStringSubmatch(line)
	var n Nanobot
	if len(m) == 5 {
		num := StringSlice2IntSlice(m[1:])
		p := Position3{num[0], num[1], num[2]}
		n = NewNanobot(p, num[3])
	}
	return n
}
