#!/usr/bin/env groovy

// tag::readInput[]
def readInput = { file ->
    def x = 0
    def y = 0
    def area = [:]
    new File(file).eachLine { line ->
        x = 0
        line.each { c ->
            area["$x,$y"] = c
            x++
        }
        y++
    }
    println "$x,$y"
    area.maxx = x-1
    area.maxy = y-1
    return area
}
// end::readInput[]

// tag::printArea[]
def printArea = { area ->
    (0..area.maxy).each { y ->
        (0..area.maxx).each { x ->
            print area["$x,$y"]
        }
        println ""
    }
}
// end::printArea[]

// tag::nextCell[]
def areaAt = { area, x, y ->
    if (x<0 || y<0 || x>area.maxx || y>area.maxy) {
        return ""
    } else {
        return area["$x,$y"]
    }
}
def count = { area, x, y ->
    def result = ['#':0,'.':0,'|':0,'':0]
    (x-1..x+1).each { xc->
        (y-1..y+1).each { yc->
            if (xc==x && yc==y) {
                // don't count the acre itself
            } else {
                result[areaAt(area, xc, yc)]++
            }
        }
    }
    return result
}
def nextCell = { area, x, y ->
    def adjacent = count(area, x, y)
    def result = area["$x,$y"]
    // An open acre will become filled with trees if three or more
    // adjacent acres contained trees. Otherwise, nothing happens.
    if (area["$x,$y"]=='.' && adjacent['|']>=3) {
        result = '|'
    }
    // An acre filled with trees will become a lumberyard if three
    // or more adjacent acres were lumberyards. Otherwise, nothing happens.
    if (area["$x,$y"]=='|' && adjacent['#']>=3) {
        result = '#'
    }
    // An acre containing a lumberyard will remain a lumberyard if
    // it was adjacent to at least one other lumberyard and at least
    // one acre containing trees. Otherwise, it becomes open.
    if (area["$x,$y"]=='#') {
        if (adjacent['#']>=1 && adjacent['|']>=1) {
            result = '#'
        } else {
            result = '.'
        }
    }
    return result
}
// end::nextCell[]

// tag::nextState[]
def nextState = { area ->
    def newArea = [maxx:area.maxx, maxy:area.maxy]
    def result = ['#':0,'.':0,'|':0,'':0]
    (0..area.maxy).each { y ->
        (0..area.maxx).each { x ->
            newArea["$x,$y"] = nextCell(area,x,y)
            result[newArea["$x,$y"]]++
        }
    }
    newArea.value = result['|']*result['#']
    return newArea
}
// end::nextState[]

def area = readInput("input.txt")
printArea(area)
def areas = [area]
1000000000.times {
    if (it%1000==0) {
        println it+1
    }
    if (it<524) {
        area = nextState(area)
        if (area in areas) {
            println it
            stop
        } else {
            areas << area
        }
    } else {
        (-3..3).each {
            println it
        area = areas[(1000000000+it)%524]
        println ""+it+": "+area.value
        }
        stop
    }
    if (it >= 1000000000-2) {
        printArea(area)
    }
}
print area.value