#!/usr/bin/env groovy

// tag::fuelCell[]
def fuelCellLevel = { x, y, serialNumber ->
    // Find the fuel cell's rack ID, which is its X coordinate plus 10.
    Integer rackID = x+10
    // Begin with a power level of the rack ID times the Y coordinate.
    Integer powerLevel = rackID * y
    // Increase the power level by the value of the grid serial number (your puzzle input).
    powerLevel += serialNumber
    // Set the power level to itself multiplied by the rack ID.
    powerLevel *= rackID
    // Keep only the hundreds digit of the power level (so 12345 becomes 3; numbers with no hundreds digit become 0).
    powerLevel = (powerLevel%1000)/100
    // Subtract 5 from the power level.
    powerLevel -= 5
}

fuelCellLevel.memoize()

// Tests
assert fuelCellLevel(3, 5, 8) == 4
// Fuel cell at  122,79, grid serial number 57: power level -5.
assert fuelCellLevel(122, 79, 57) == -5
// Fuel cell at 217,196, grid serial number 39: power level  0.
assert fuelCellLevel(217, 196, 39) == 0
// Fuel cell at 101,153, grid serial number 71: power level  4.
assert fuelCellLevel(101, 153, 71) == 4

// end::fuelCell[]

// tag::square[]
def squareLevel = { x, y, serialNumber, size ->
    Integer powerLevel = 0
    (x..x+(size-1)).each { x2 ->
        (y..y+(size-1)).each { y2 ->
            powerLevel += fuelCellLevel(x2, y2, serialNumber)
        }
    }
    return powerLevel
}

squareLevel.memoize()

// Tests
// For grid serial number 18, the largest total 3x3 square has a
// top-left corner of 33,45 (with a total power of 29)
assert squareLevel(33, 45, 18, 3) == 29

// For grid serial number 42, the largest 3x3 square's
// top-left is 21,61 (with a total power of 30)
assert squareLevel(21, 61, 42, 3) == 30

// end::square[]

// tag::largest[]
def findLargest= { serialNumber, size ->
    def largest = [x:0, y:0, powerLevel:0]
    (1..300-(size-1)).each { x ->
        (1..300-(size-1)).each { y ->
            def powerLevel = squareLevel(x, y, serialNumber, size)
            if (powerLevel>largest.powerLevel) {
                largest.powerLevel = powerLevel
                largest.x = x
                largest.y = y
            }
        }
    }
    return largest
}

findLargest.memoize()

// Tests
// For grid serial number 18, the largest total 3x3 square has a
// top-left corner of 33,45 (with a total power of 29)
assert findLargest(18, 3) == [x: 33, y: 45, powerLevel: 29]

// For grid serial number 42, the largest 3x3 square's
// top-left is 21,61 (with a total power of 30)
assert findLargest(42, 3) == [x: 21, y: 61, powerLevel: 30]
// end::largest[]

// tag::dynamicLargest[]

def findDynamicLargest = { serialNumber ->
    def largest = [x:0, y:0, powerLevel:0, size: 0]
    (1..300).each { size ->
        println size
        def result = findLargest(serialNumber, size)
        if (result.powerLevel>largest.powerLevel) {
            largest.powerLevel = result.powerLevel
            largest.x = result.x
            largest.y = result.y
            largest.size = size
        }
    }
    return largest
}
// Tests
// For grid serial number 18, the largest total square
// (with a total power of 113) is 16x16 and has a top-left
// corner of 90,269, so its identifier is 90,269,16.
// assert findDynamicLargest(18) == [x: 90, y: 269, powerLevel: 113, size: 16]

// For grid serial number 42, the largest total square
// (with a total power of 119) is 12x12 and has a top-left
// corner of 232,251, so its identifier is 232,251,12.
// assert findDynamicLargest(42) == [x: 232, y: 151, powerLevel: 119, size: 12]

// end::dynamicLargest[]

// tag::starOne[]
println "Star one: "+findLargest(4172, 3)
// end::starOne[]

// tag::starTwo[]
println "Star two: "+findDynamicLargest(4172)
// end::starTwo[]

