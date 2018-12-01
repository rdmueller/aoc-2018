package main
import (
    "fmt"
    "io/ioutil"
    "strings"
    "strconv"
)
func main() {
    byteContent, err := ioutil.ReadFile("input.txt")
    if err != nil {
        fmt.Print(err)
    }

    stringContent := string(byteContent)
    lines := strings.Split(stringContent, "\n")
    
    sum := 0
    for _, v := range lines {
        if len(v) == 0 {
            continue
        }
        num, _ := strconv.Atoi(v)
        sum += num
    }
    fmt.Println(sum)
}