package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"io"
)

func getPipeReader() *bufio.Reader {
	stat, err := os.Stdin.Stat()
    if err != nil {
        panic(err)
    }

	if stat.Mode() & os.ModeNamedPipe == 0 {
		fmt.Println("This program only accepts piped input.")
		os.Exit(1)
	}

	reader := bufio.NewReader(os.Stdin)
	return reader
}

// parse all input lines into string slice
func getInput(sorted bool) []string {
	reader := getPipeReader()
	var input []string

	for {
		lineBytes, _, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		lineString := string(lineBytes)
		input = append(input, lineString)
	}

	if sorted {
		sort.Strings(input)
	}
	return input
}