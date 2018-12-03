package main

import (
	"bufio"
	"fmt"
	"os"
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
