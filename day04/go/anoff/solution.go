package main

import (
	"fmt"
	"strings"
	"strconv"	
)

// tag::StructDef[]
type Shift struct {
	guard int // guard id
	date string // date str MM-DD
	asleep []int // array of minutes being asleep (each entry is a minute where guard is asleep)
}
// end::StructDef[]

func main() {
	input := getInput(true)
	
	fmt.Println(strings.Join(input, "\n"))
	strategy1(input)
}

func strategy1(log []string /* sorted input */) {
	var shifts []Shift
	activeShift := Shift{guard: -1}
	fellAsleep := -1
	for _, entry := range log {
		guardId := detectShiftChange(entry)
		date := extractDate(entry)
		if guardId > -1 {
			// check if this is NOT the first shift
			if activeShift.guard > -1 {
				shifts = append(shifts, activeShift)
			}
			// fmt.Printf("Guard change detected on %s from %d to %d\n", date, activeShift.guard, guardId)
			activeShift.guard = guardId
			activeShift.asleep = []int{}
			fellAsleep = -1
		}
		activeShift.date = date

		if strings.Contains(entry, "falls asleep") {
			fellAsleep = extractMinute(entry)
		}

		if strings.Contains(entry, "wakes up") {
			if fellAsleep > -1 {
				wakeupMin := extractMinute(entry)
				for i := fellAsleep; i <= wakeupMin; i++ {
					activeShift.asleep = append(activeShift.asleep, i)
				}
			} else {
				fmt.Printf("🤯 Guard %d woke up withouth falling asleep :ooo\n", activeShift.guard)
			}
		}
	}
	// make sure last shift is also stored (no shift change in file)
	shifts = append(shifts, activeShift)

	fmt.Println(shifts)
}

// extract the guard id from a new shift entry
// returns -1 if no shift change detected
func detectShiftChange(entry string) int {
	if strings.Contains(entry, "begins shift") {
		id := strings.Split(strings.Split(entry, "#")[1], " ")[0]
		num, _ := strconv.Atoi(id)
		return num
	}
	return -1
}

func extractDate(entry string) string {
	date := entry[6:11]
	return date
}

func extractMinute(entry string) int {
	min := entry[15:17]
	num, _ := strconv.Atoi(min)
	return num
}