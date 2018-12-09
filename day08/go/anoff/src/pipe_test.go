package main
import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestGetInput(t *testing.T) {
	assert.Equal(t, 1, len(AggregateInputStream()))
}
