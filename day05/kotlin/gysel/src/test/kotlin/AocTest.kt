import io.kotlintest.shouldBe
import org.junit.Test

class AocTest {

    @Test
    fun `remove duplicates`() {
        removeDups("dabAcCaCBAcCcaDA") shouldBe "dabCBAcaDA"
    }

}