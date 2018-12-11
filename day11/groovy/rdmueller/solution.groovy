#!/usr/bin/env groovy

// tag::helper[]
List readInput(fileName) {
    new File(fileName).text
            .split("\n")
            .collect{ line ->
                // position=<-3,  6> velocity=< 2, -1>
                matcher = line =~ /.+<([-0-9, ]+)>.+<([-0-9, ]+)>.*/
                line = matcher[0][1]+","+matcher[0][2]
                line.split(",")
                .collect{ it.trim() as Integer }
            }
}
// end::helper[]

// tag::starOne[]
// end::starOne[]


