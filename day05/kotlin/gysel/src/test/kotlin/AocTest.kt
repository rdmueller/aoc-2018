package ch.mgysel.aoc

import io.kotlintest.shouldBe
import org.junit.Test

class AocTest {

    @Test
    fun `remove duplicates`() {
        performReaction("dabAcCaCBAcCcaDA") shouldBe "dabCBAcaDA"
    }

}