package main

import (
	"io/ioutil"
	"strings"
	"strconv"
)

func readInput(filepath string) []string {
	b, err := ioutil.ReadFile(filepath)
	if err != nil {
			panic(err)
	}

	s := string(b)
	return strings.Split(s, "\n")
}

func StringSlice2IntSlice(strNums []string) []int {
	var nums []int
	for _, s := range strNums {
		n, _ := strconv.Atoi(s)
		nums = append(nums, n)
	}
	return nums
}