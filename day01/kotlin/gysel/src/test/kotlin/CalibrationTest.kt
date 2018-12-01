import io.kotlintest.shouldBe
import org.junit.Test

class CalibrationTest {

    @Test
    fun `calculate resulting frequency`() {
        listOf(
            listOf(1, 1, 1) to 3,
            listOf(1, 1, -2) to 0,
            listOf(-1, -2, -3) to -6
        ).forEach {
            val (data, result) = it
            calculateResultingFrequency(data) shouldBe result
        }
    }

    @Test
    fun `first repeated frequency is 0`() {
        calculateFirstRepetition(listOf(1, -1)) shouldBe 0
    }

    @Test
    fun `first repeated frequency is 10`() {
        calculateFirstRepetition(listOf(3, 3, 4, -2, -4)) shouldBe 10
    }

    @Test
    fun `first repeated frequency is 5`() {
        calculateFirstRepetition(listOf(-6, 3, 8, 5, -6)) shouldBe 5
    }

    @Test
    fun `first repeated frequency is 14`() {
        calculateFirstRepetition(listOf(7, 7, -2, -7, -4)) shouldBe 14
    }
}