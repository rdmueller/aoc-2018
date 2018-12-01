import kotlin.streams.toList

fun main(args: Array<String>) {

    val frequencyChanges: List<Int> = openStream().bufferedReader().use { reader ->
        reader.lines()
            .map(Integer::valueOf)
            .toList()
    }

    val resultingFrequency = calculateResultingFrequency(frequencyChanges)
    val firstRepetition = calculateFirstRepetition(frequencyChanges)

    println("Resulting Frequency: $resultingFrequency")
    println("First repeated frequency: $firstRepetition")
}

fun calculateResultingFrequency(changes: List<Int>) = changes.sum()

fun calculateFirstRepetition(changes: List<Int>): Int {
    var frequency = 0
    val frequencies = mutableSetOf(frequency)
    generateSequence { changes }
        .flatten()
        .find {
            frequency += it
            frequencies.contains(frequency).also { frequencies.add(frequency) }
        }
    return frequency
}

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/day01.txt")