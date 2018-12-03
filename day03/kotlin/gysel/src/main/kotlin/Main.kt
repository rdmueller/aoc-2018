import kotlin.system.measureTimeMillis

fun main() {

    /*
        part one
     */
    val claims = openStream().reader().useLines { lines: Sequence<String> ->
        lines.filter(String::isNotEmpty).map(::parse).toList()
    }
    println("Parsed ${claims.size} claims")

    val time = measureTimeMillis {

        val fabric = Fabric(claims)
        println("Pieces used more than once: ${fabric.numberOfUsedPieces(2)}") // should be 111485
        /*
            part two
         */
        val unique = fabric.findUniqueClaims()
        println("Found ${unique.size} unique claim(s): ${unique.joinToString()}")
    }

    println("Executed in ${time}ms")

}

class Fabric(private val claims: List<Claim>) {
    private val usedPieces = mutableMapOf<Coordinates, MutableSet<Claim>>()

    init {
        claims.forEach(::addClaim)
    }

    fun numberOfUsedPieces(numberOfClaims: Int) = coordinatesWith(numberOfClaims).count()

    fun findUniqueClaims(): List<Claim> = claims - overlapping()

    private fun addClaim(claim: Claim) {
        (claim.left..(claim.left + claim.width - 1)).forEach { left ->
            (claim.top..(claim.top + claim.height - 1)).forEach { top ->
                val coordinates = Coordinates(left, top)
                usedPieces.compute(coordinates) { _, set ->
                    set?.also { it.add(claim) } ?: mutableSetOf(claim)
                }
            }
        }
    }

    private fun overlapping(): Sequence<Claim> {
        return coordinatesWith(2).map { it.value }.flatten().distinct()
    }

    private fun coordinatesWith(numberOfClaims: Int) = usedPieces
            .asSequence()
            .filter { it.value.size >= numberOfClaims }
}

data class Coordinates(val left: Int, val top: Int)

data class Claim(val id: Int,
                 val left: Int,
                 val top: Int,
                 val width: Int,
                 val height: Int)

fun parse(line: String): Claim {
    // #123 @ 3,2: 5x4
    val (id, left, top, width, height) =
            line.drop(1)
                    .split("@", ",", ":", "x")
                    .map(String::trim)
                    .map(String::toInt)

    return Claim(id, left, top, width, height)
}

// object {} is a little hack as Java needs a class to open a resource from the classpath
private fun openStream() = object {}::class.java.getResourceAsStream("/day03.txt")