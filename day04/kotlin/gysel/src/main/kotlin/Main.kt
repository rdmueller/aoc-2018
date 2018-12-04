import kotlin.system.measureTimeMillis

fun main() {

    val millis = measureTimeMillis {

        val data = openStream().reader().useLines { lines: Sequence<String> ->
            lines.filter(String::isNotEmpty)
                    .sorted()
                    .map(::parse)
                    .toList()
        }
        println("Parsed ${data.size} records")

        val naps: List<GuardNap> = data.fold(ProcessingState()) { acc, record ->
            val action = record.action
            when {
                action.startsWith("Guard") -> acc.processGuard(action)
                action == "falls asleep" -> acc.processSleep(record.time)
                action == "wakes up" -> acc.processWakeup(record.date, record.time)
                else -> throw IllegalStateException("Unexpected action '$action'!")
            }
            acc
        }.naps
        println("Found ${naps.size} naps")

        // Part 1

        val sleepHabitsOfGuards: List<SleepHabits> = naps.groupBy { it.guard }.map { entry ->
            val (sleepyGuard, napsOfGuard) = entry
            calculateSleepHabitsOfGuard(napsOfGuard, sleepyGuard)
        }

        val guardWithLongestNapDuration = sleepHabitsOfGuards.maxBy { it.totalSleepDuration }
                ?: throw IllegalStateException("No max found!")
        val (guard, minute, total) = guardWithLongestNapDuration
        println("Guard $guard slept for a total of $total")
        println("Most sleepy minute of guard $guard was minute $minute")
        println("Answer of part 1 is ${guard * minute}")

        // Part 2

        val guardMostLikelyToBeSleeping = sleepHabitsOfGuards.maxBy { it.numberOfNapsAtMinuteOfMostNaps }
                ?: throw IllegalStateException("No max found!")
        println("Most sleepy guard is ${guardMostLikelyToBeSleeping.guard}, most likely asleep at 00:${guardMostLikelyToBeSleeping.minuteOfMostNaps}")
        println("Answer of part 2 is ${guardMostLikelyToBeSleeping.guard * guardMostLikelyToBeSleeping.minuteOfMostNaps}")
    }

    println("Calculated solution in ${millis}ms")
}

data class Record(val date: String, val time: String, val action: String)

data class SleepHabits(val guard: Int,
                       val minuteOfMostNaps: Int,
                       val numberOfNapsAtMinuteOfMostNaps: Int,
                       val totalSleepDuration: Int)

data class GuardNap(val guard: Int, val date: String, val timeStart: String, val timeEnd: String) {

    fun calculateDuration(): Int {
        val from = extractMinutes(timeStart)
        val to = extractMinutes(timeEnd)
        return to - from
    }

    fun minutes() = (extractMinutes(timeStart) until extractMinutes(timeEnd)).toList()

    private fun extractMinutes(time: String) = time.split(":")[1].toInt()
}

private fun calculateSleepHabitsOfGuard(napsOfSleepyGuard: List<GuardNap>, guard: Int): SleepHabits {
    val mostSleepyMinute = napsOfSleepyGuard.flatMap { it.minutes() }
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            ?: throw IllegalStateException("No max found!")
    val totalSleepDuration = napsOfSleepyGuard.asSequence().map { it.calculateDuration() }.sum()
    return SleepHabits(guard, mostSleepyMinute.key, mostSleepyMinute.value, totalSleepDuration)
}

fun parse(line: String): Record {
    // [1518-03-18 00:03] Guard #3529 begins shift
    val (date, time, action) = line.split(delimiters = *arrayOf(" "), limit = 3)
    return Record(date.drop(1), time.dropLast(1), action)
}

fun extractGuardId(action: String): Int {
    return action.split(" ")[1].drop(1).toInt()
}


class ProcessingState {
    val naps = mutableListOf<GuardNap>()
    private var currentGuard: Int? = null
    private var startedSleepTime: String? = null

    fun processGuard(action: String) {
        currentGuard = extractGuardId(action)
    }

    fun processSleep(time: String) {
        if (currentGuard == null) throw IllegalStateException("No guard defined yet!")
        startedSleepTime = time
    }

    fun processWakeup(date: String, time: String) {
        naps.add(GuardNap(currentGuard!!, date, startedSleepTime!!, time))
        startedSleepTime = null
    }

}

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/day04.txt")