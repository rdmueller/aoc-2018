package ch.mgysel.aoc

import java.util.*
import kotlin.system.measureTimeMillis

fun main() {

    val millis = measureTimeMillis {

        val data = openStream().reader().readText()

        println("Data is ${data.length} characters long")

        val result = performReaction(data)
        println("Result has length ${result.length}")

        // tag::partTwo[]
        val (letter, resultingLength) = ('a'..'z').asSequence().map { letter ->
            val filteredData = result.filter { it.toLowerCase() != letter }
            val resultingLength = performReaction(filteredData).length
            println("Simulating $letter, new length is $resultingLength")
            letter to resultingLength
        }.minBy { it.second } ?: throw IllegalStateException("no maximum found!")
        // end::partTwo[]

        println("Solution of part two is '$letter' with a resulting length of $resultingLength")
    }

    println("Calculated solution in ${millis}ms")
}

// tag::performReaction[]
fun performReaction(data: String): String {

    val toProcess = LinkedList<Char>()
    data.forEach { toProcess.add(it) }

    val processed = LinkedList<Char>()

    while (toProcess.isNotEmpty()) {
        when {
            processed.isEmpty() -> processed.addFirst(toProcess.poll())
            charactersAreReacting(processed.peek(), toProcess.peek()) -> {
                processed.remove()
                toProcess.remove()
            }
            else -> processed.addFirst(toProcess.poll())
        }
    }

    val result = StringBuilder(processed.size)
    processed.forEach { result.append(it) }
    return result.toString().reversed()
}
// end::performReaction[]

// tag::charactersAreReacting[]
private fun charactersAreReacting(first: Char, second: Char): Boolean {
    return first != second && first.equals(second, true)
}
// end::charactersAreReacting[]

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/data.txt")