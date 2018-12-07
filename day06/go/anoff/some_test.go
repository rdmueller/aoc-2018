package main

import "testing"

func TestManhattenDistance(t *testing.T) {
    dist1 := manhattenDistance(0, 0, 5, 5)
    if dist1 != 10 {
       t.Errorf("Distance was incorrect, got: %d, want: %d.", dist1, 10)
    }

    dist2 := manhattenDistance(5, 0, 0, 5)
    if dist2 != 10 {
       t.Errorf("Distance was incorrect, got: %d, want: %d.", dist2, 10)
    }
}