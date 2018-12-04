import java.lang.IllegalStateException

fun main() {

    /*
        part one
     */
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
            action == "falls asleep" -> acc.processSleep(record.date, record.time)
            action == "wakes up" -> acc.processWakeup(record.time)
            else -> throw IllegalStateException("Unexpected action '$action'!")
        }
        acc
    }.naps

    println("Found ${naps.size} naps")

    val guardsAndNapDuration: Map<Int, Int> = naps.asSequence()
            .groupingBy { it.guard }
            .aggregate { _, accumulator, element, _ ->
                (accumulator ?: 0) + element.calculateDuration()
            }

    val guardIds = naps.asSequence().map(GuardNap::guard).distinct().sorted().toList()
    println("guards: $guardIds")

    //naps.take(50).forEach(System.out::println)
    println("guardsAndNapDuration: $guardsAndNapDuration")
    val maxByNapduration = guardsAndNapDuration.maxBy { it.value }
    println("guard with most naps: $maxByNapduration")
    val napsOfSleepyGuard = naps.filter { it.guard == 641 }
    //napsOfSleepyGuard.forEach(System.out::println)
    findMostSleepyMinute(napsOfSleepyGuard)
    // correct: guard 641, minute 41

    /*
        Part 2

        Strategy 2: Of all guards, which guard is most frequently asleep on the same minute?

        In the example above, Guard #99 spent minute 45 asleep more than any other guard or minute - three
        times in total. (In all other cases, any guard spent any minute asleep at most twice.)

        What is the ID of the guard you chose multiplied by the minute you chose? (In the above example,
        the answer would be 99 * 45 = 4455.)
     */

    val napsByGuard = naps.groupBy { it.guard }

    napsByGuard.forEach { guardId, napsOfGuard ->
        println("checking guard $guardId")
        findMostSleepyMinute(napsOfGuard)
    }

}

private fun findMostSleepyMinute(napsOfSleepyGuard: List<GuardNap>) {
    val sleepingByMinute = napsOfSleepyGuard.flatMap { it.minutes() }.groupingBy { it }.eachCount().toSortedMap()
    val mostSleepyMinute = sleepingByMinute.maxBy { it.value }!!
    println("most sleepy minute is ${mostSleepyMinute.key}, was asleep for ${mostSleepyMinute.value} min")
}

fun parse(line: String): Record {
    // [1518-03-18 00:03] Guard #3529 begins shift
    val (date, time, action) = line.split(delimiters = *arrayOf(" "), limit = 3)
    return Record(date.drop(1), time.dropLast(1), action)
}

fun extractGuardId(action: String): Int {
    return action.split(" ")[1].drop(1).toInt()
}

data class Record(val date: String, val time: String, val action: String)
data class GuardNap(val guard: Int, val date: String, val timeStart: String, val timeEnd: String) {

    fun calculateDuration(): Int {
        val from = extractMinutes(timeStart)
        val to = extractMinutes(timeEnd)
        return to - from
    }

    fun minutes() = (extractMinutes(timeStart) until extractMinutes(timeEnd)).toList()

    private fun extractMinutes(time: String) = time.split(":")[1].toInt()
}

class ProcessingState {
    val naps = mutableListOf<GuardNap>()
    private var currentGuard: Int? = null
    private var startedSleepTime: String? = null
    private var startedSleepDate: String? = null

    fun processGuard(action: String) {
        currentGuard = extractGuardId(action)
    }

    fun processSleep(date: String, time: String) {
        assert(currentGuard != null)
        startedSleepDate = date
        startedSleepTime = time
    }

    fun processWakeup(time: String) {
        naps.add(GuardNap(currentGuard!!, startedSleepDate!!, startedSleepTime!!, time))
        startedSleepDate = null
        startedSleepTime = null
    }


}


// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/day04.txt")