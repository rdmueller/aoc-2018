import io.kotlintest.matchers.haveSize
import io.kotlintest.should
import io.kotlintest.shouldBe
import org.junit.Test

class BoxIdCalculator {

    @Test
    fun `parse claim`() {
        val actual = parse("#123 @ 3,2: 5x4")
        with (actual) {
            id shouldBe 123
            left shouldBe 3
            top shouldBe 2
            width shouldBe 5
            height shouldBe 4
        }
    }

    @Test
    fun `scenario from documented sample`() {
        val claims = listOf(
                "#1 @ 1,3: 4x4",
                "#2 @ 3,1: 4x4",
                "#3 @ 5,5: 2x2").map(::parse)

        val fabric = Fabric(claims)
        fabric.numberOfUsedPieces(2) shouldBe 4
        val unique = fabric.findUniqueClaims()
        unique should haveSize(1)
        unique.first().id shouldBe 3
    }

}