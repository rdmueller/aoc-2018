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
Map getMinMax(stars) {
    [
        minX: stars.min {it[0]}[0],
        maxX: stars.max {it[0]}[0],
        minY: stars.min {it[1]}[1],
        maxY: stars.max {it[1]}[1]
    ]
}
void printStars(stars, minMax) {
    def lines = [:]
    println "-"*80
    (minMax.minY..minMax.maxY).each { y ->
        lines[y] = "."*(minMax.maxX-minMax.minX+3)
    }
    stars.each { star ->
        pos = star[0]-minMax.minX+1
        lines[star[1]] = lines[star[1]][0..pos-1] + "*" + lines[star[1]][pos+1..-1]
    }
    lines.each { num, line ->
        println ">>"+line
    }
}
List moveStars(stars) {
    List newStars = []
    stars.each { star ->
        newStars << [
            star[0]+star[2],
            star[1]+star[3],
            star[2],
            star[3]
        ]
    }
    return newStars
}

List input
stars = readInput("input.txt")

minMax = getMinMax(stars)
println minMax
def newMinY = minMax.maxY-minMax.minY
def currentMinY = newMinY
while (newMinY<=currentMinY) {
    if (currentMinY<20) {
        printStars(stars, minMax)
    } else {
        print "."
    }
    stars = moveStars(stars)
    minMax = getMinMax(stars)
    currentMinY = newMinY
    newMinY = minMax.maxY-minMax.minY
}

//stars = readInput("input.txt")
// end::starOne[]

// tag::starTwo[]
// end::starTwo[]

