package main
import (
	"testing"
	"github.com/stretchr/testify/assert"
)

type game struct {
	players int
	rounds int
}
func TestMarbleGame(t *testing.T) {
	m := make(map[int]game)
	m[32] = game{7, 25}
	m[8317] = game{10, 1618}
	m[146373] = game{13, 7999}
	m[2764] = game{17, 1104}
	m[54718] = game{21, 6111}
	m[37305] = game{30, 5807}
	for highscore, setup := range m {
		_, scores := marbleGame(setup.players, setup.rounds)
		hs := getHighscore(scores)
		assert.Equal(t, highscore, hs, "Wrong highscore for %d players doing %d rounds", setup.players, setup.rounds)
	}
}

func TestNextIndex(t *testing.T) {
	assert.Equal(t, 1, nextIndex(0, 1))
	assert.Equal(t, 3, nextIndex(1, 3))
	assert.Equal(t, 5, nextIndex(3, 10))
	assert.Equal(t, 1, nextIndex(7, 8))
	assert.Equal(t, 1, nextIndex(15, 16))
}