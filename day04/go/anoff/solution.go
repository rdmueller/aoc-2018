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
	
	// fmt.Println(strings.Join(input, "\n"))
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
				for i := fellAsleep; i < wakeupMin; i++ {
					activeShift.asleep = append(activeShift.asleep, i)
				}
			} else {
				fmt.Printf("🤯 Guard %d woke up withouth falling asleep :ooo\n", activeShift.guard)
			}
		}
	}
	// make sure last shift is also stored (no shift change in file)
	shifts = append(shifts, activeShift)

	// fmt.Println(shifts)

	// find longest sleeping guard
	guardsSleepDuration := make(map[int]int)
	for _, shift := range shifts {
		if _, exists := guardsSleepDuration[shift.guard]; !exists { // see https://stackoverflow.com/questions/2050391/how-to-check-if-a-map-contains-a-key-in-go
			guardsSleepDuration[shift.guard] = sumSleepTimes(shifts, shift.guard)
		}
	}
	// fmt.Println(guardsSleepDuration)

	longestSleepingGuard := -1
	longestSleep := -1
	for guard, duration := range guardsSleepDuration {
		if duration > longestSleep {
			longestSleep = duration
			longestSleepingGuard = guard
		}
	}
	fmt.Printf("Longest sleeping guard: #%d @ %d min\n", longestSleepingGuard, longestSleep)

	// find out which minute he was asleep the most
	sleepMap := make(map[int]int) // key=min0-59, val=#shifts asleep
	for _, shift := range shifts {
		if shift.guard == longestSleepingGuard {
			for min := 0; min < 60; min++ {
				for _, asleepMin := range shift.asleep {
					if asleepMin == min {
						sleepMap[min]++
					}
				}
			}
		}
	}

	// fmt.Println(sleepMap)
	maxShiftsAsleep := 0
	for min, duration := range sleepMap {
		if sleepMap[maxShiftsAsleep] < duration {
			maxShiftsAsleep = min
		}
	}
	fmt.Printf("Solution for part1: %d \nat minute %d guard#%d is asleep the most often (%d shifts)\n", maxShiftsAsleep * longestSleepingGuard, maxShiftsAsleep, longestSleepingGuard, sleepMap[maxShiftsAsleep])
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

// get the overall sum of sleep times of a specific guard
func sumSleepTimes(shifts []Shift, guardId int) int {
	counter := 0
	for _, shift := range shifts {
		if shift.guard == guardId {
			counter += len(shift.asleep)
		}
	}
	return counter
}
