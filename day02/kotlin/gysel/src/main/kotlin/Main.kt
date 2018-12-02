import java.util.concurrent.atomic.AtomicInteger

fun main(args: Array<String>) {
    /*
        part one
     */
    val exactlyTwo = AtomicInteger(0)
    val exactlyThree = AtomicInteger(0)

    openStream().reader().useLines { lines: Sequence<String> ->
        lines.map(::groupByCharacter)
                .forEach {
                    if (it.containsValue(2))
                        exactlyTwo.incrementAndGet()
                    if (it.containsValue(3))
                        exactlyThree.incrementAndGet()

                }
    }
    println("Exactly two: $exactlyTwo")
    println("Exactly three: $exactlyThree")
    println("Checksum: ${exactlyTwo.get() * exactlyThree.get()}")

    /*
        part two
     */
    val lines = openStream().reader().readLines()
    calculateCombinations(lines)
            .filter { numberOfChangedCharacters(it) == 1 }
            .forEach { pair ->
                println("found correct box id: $pair")
                val commonCharacters = pair.first.toCharArray()
                        .zip(pair.second.toCharArray())
                        .filter { it.first == it.second }
                        .map { it.first }
                        .joinToString("")
                println("common characters are: $commonCharacters")
            }

}

fun groupByCharacter(input: String): Map<Char, Int> {
    return input.toCharArray()
            .groupBy { it }
            .map { it.key to it.value.size }
            .toMap()
}

fun calculateCombinations(values: List<String>): Sequence<Pair<String, String>> {
    val lastIndex = values.size - 1
    // combine two Ranges into a List<List<Pair>> and then flatten() them
    return (0..(lastIndex - 1)).asSequence().map { left ->
        ((left + 1)..lastIndex).map { right ->
            values[left] to values[right]
        }
    }.flatten()
}

fun numberOfChangedCharacters(pair: Pair<String, String>): Int {
    val (a, b) = pair
    if (a.length != b.length) throw IllegalStateException("Strings need to be of equals size!")
    return a.toCharArray().zip(b.toCharArray()).count { it.first != it.second }
}

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/day02.txt")