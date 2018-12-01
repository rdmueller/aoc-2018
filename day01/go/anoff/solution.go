package main
import (
    "fmt"
    "io/ioutil"
    "strings"
    "strconv"
    "math"
)
func main() {
    byteContent, err := ioutil.ReadFile("./input.txt")
    if err != nil {
        fmt.Print(err)
    }

    stringContent := string(byteContent)
    lines := strings.Split(stringContent, "\n")
    
    // Part 1
    currentFrequency := 0
    for _, v := range lines {
        if len(v) == 0 {
            continue
        }
        num, _ := strconv.Atoi(v)
        currentFrequency += num
    }
    fmt.Printf("Solution for part 1: %d\n", currentFrequency)

    // Part 2
    var frequencies []int
    currentFrequency = 0
    frequencies = append(frequencies, currentFrequency)
    for i := 0; i < 1000; i++ { // loop multiple times over the list
        for _, v := range lines {
            if len(v) == 0 {
                continue
            }
            num, _ := strconv.Atoi(v)
            currentFrequency += num
            
            isMatch, _ := hasElement(frequencies, currentFrequency)
            if isMatch {
                fmt.Printf("Solution for part 2: %d, found in iteration %d\n", currentFrequency, i)
                return
            }
            frequencies = append(frequencies, currentFrequency)
            // fmt.Printf("number of frequencies: %d, currentFreq: %d, minDiff: %d\n", len(frequencies), currentFrequency, diff)
        }
    }
}

func hasElement (arr []int, elm int) (bool, int) {
    minDiff := math.Inf(0)
    for _, n := range arr {
        diff := math.Abs(float64(n - elm))
        if diff < minDiff {
            minDiff = diff
        }
        if n == elm {
            return true, int(minDiff)
        }
    }
    return false, int(minDiff)
}