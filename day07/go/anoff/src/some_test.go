package main
import (
	"testing"
	"github.com/stretchr/testify/assert"
)

func TestTaskDuration(t *testing.T) {
	assert.Equal(t, 61,getTaskDuration("A"))
	assert.Equal(t, 86, getTaskDuration("Z"))
}
