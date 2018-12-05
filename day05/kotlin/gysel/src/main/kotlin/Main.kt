import kotlin.system.measureTimeMillis

fun main() {

    val millis = measureTimeMillis {

        val data = openStream().reader().readText()

        println("Data is ${data.length} characters long")

        val result = removeDups(data)
        println("Result has length ${result.length}")

        val solutionTwo = ('a'..'z').map { letter ->
            val filteredData = result.filter { it.toLowerCase() != letter }
            val resultingLength = removeDups(filteredData).length
            println("Simulating $letter, new length is $resultingLength")
            letter to resultingLength
        }.minBy { it.second }
        println("Solution of part two is $solutionTwo")
    }

    println("Calculated solution in ${millis}ms")
}

fun removeDups(data: String): String {
    var result = StringBuilder(data)
    var position = 1
    while (position < result.length) {
        val current = result[position]
        val previous = result[position - 1]
        if (doReact(current, previous)) {
            result = result.deleteCharAt(position)
            result = result.deleteCharAt(position - 1)
            position -= 2
        }
        if (position < 0) position = 1 else position++
    }
    return result.toString()
}

private fun doReact(current: Char, next: Char) = current != next && current.equals(next, true)

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/data.txt")