package helpers

import (
	"bufio"
	"fmt"
	"os"
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
func AggregateInputStream() []string {
	reader := getPipeReader()
	var input []string

	isPrefixK1 := false // store last prefix state
	for {
		lineBytes, isPrefix, err := reader.ReadLine()
		if err == io.EOF {
			break
		}
		lineString := string(lineBytes)
		if (isPrefixK1 || isPrefix) && len(input) > 0 { // line is too long, chunking into multiple reads
			input[len(input)-1] += lineString
		} else { // real new line
			input = append(input, lineString)
		}
		isPrefixK1 = isPrefix
	}

	return input
}