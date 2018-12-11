package main
import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestGetHundreds(t *testing.T) {
	assert.Equal(t, 9, getHundreds(949))
	assert.Equal(t, 0, getHundreds(10))
	assert.Equal(t, 2, getHundreds(789210))
}

func TestGetPowerLevel(t *testing.T) {
	assert.Equal(t, 4, getPowerLevel(&Cell{3, 5}, 8))
	assert.Equal(t, -5, getPowerLevel(&Cell{122, 79}, 57))
	assert.Equal(t, 4, getPowerLevel(&Cell{101, 153}, 71))
}
