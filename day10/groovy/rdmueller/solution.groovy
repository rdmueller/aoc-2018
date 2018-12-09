#!/usr/bin/env groovy

// tag::helper[]
List readInput(fileName) {
    new File(fileName).text
            .split("\n")
            .collect{ line ->
                line.split(",")
                .collect{ it.trim() }
            }
}
// end::helper[]

// tag::starOne[]
List input
input = readInput("testInput.txt")
println input
input = readInput("input.txt")
// end::starOne[]

// tag::starTwo[]
// end::starTwo[]
