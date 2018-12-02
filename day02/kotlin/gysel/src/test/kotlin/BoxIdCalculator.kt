import io.kotlintest.shouldBe
import org.junit.Test

class BoxIdCalculator {

    @Test
    fun `count characters`() {
        groupByCharacter("abcdef") shouldBe mapOf(
                'a' to 1,
                'b' to 1,
                'c' to 1,
                'd' to 1,
                'e' to 1,
                'f' to 1)

        groupByCharacter("bababc") shouldBe
                mapOf('b' to 3, 'a' to 2, 'c' to 1)

        groupByCharacter("abbcde") shouldBe mapOf(
                'a' to 1,
                'b' to 2,
                'c' to 1,
                'd' to 1,
                'e' to 1)
    }

    @Test
    fun `count number of changed characters`() {
        numberOfChangedCharacters("abcde" to "axcye") shouldBe 2
        numberOfChangedCharacters("fghij" to "fguij") shouldBe 1
        numberOfChangedCharacters("abc" to "abc") shouldBe 0
    }

    @Test
    fun `calculate all possible pairs`() {
        calculateCombinations(listOf("a", "b", "c")).toList() shouldBe listOf(
                "a" to "b",
                "a" to "c",
                "b" to "c")
    }

}