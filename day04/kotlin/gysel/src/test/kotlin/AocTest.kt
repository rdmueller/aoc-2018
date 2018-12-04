import io.kotlintest.shouldBe
import org.junit.Test

class AocTest {

    @Test
    fun `extract guard id`() {
        val actual = extractGuardId("Guard #1973 begins shift")
        actual shouldBe 1973
    }

    @Test
    fun `calculate nap duration`() {
        val nap = GuardNap(1,"","00:10","00:15")
        nap.calculateDuration() shouldBe 5
    }

    @Test
    fun `extract nap minutes`() {
        val nap = GuardNap(1,"","00:05","00:08")
        nap.minutes() shouldBe listOf(5,6,7)
    }
}