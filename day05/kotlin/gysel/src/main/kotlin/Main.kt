package ch.mgysel.aoc

import kotlin.system.measureTimeMillis

fun main() {

    val millis = measureTimeMillis {

        val data = openStream().reader().readText()

        println("Data is ${data.length} characters long")

        val result = performReaction(data)
        println("Result has length ${result.length}")

        val (letter, resultingLength) = ('a'..'z').map { letter ->
            val filteredData = result.filter { it.toLowerCase() != letter }
            val resultingLength = performReaction(filteredData).length
            println("Simulating $letter, new length is $resultingLength")
            letter to resultingLength
        }.minBy { it.second } ?: throw IllegalStateException("no maximum found!")

        println("Solution of part two is '$letter' with a resulting length of $resultingLength")
    }

    println("Calculated solution in ${millis}ms")
}

fun performReaction(data: String): String {
    var result = StringBuilder(data)
    var position = 1
    while (position < result.length) {
        val current = result[position]
        val previous = result[position - 1]
        if (checkWhetherCharactersReact(current, previous)) {
            result = result.deleteCharAt(position)
            result = result.deleteCharAt(position - 1)
            position -= 2
        }
        if (position < 0) position = 1 else position++
    }
    return result.toString()
}

private fun checkWhetherCharactersReact(first: Char, second: Char) = first != second && first.equals(second, true)

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/data.txt")