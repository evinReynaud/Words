package anagramfinder

import anagramfinder.models.AnagramMetadata
import org.junit.Test
import kotlin.test.assertEquals

class AnagramMetadataTest {
    @Test
    fun shouldSortSmallSizeFirstThenAlphabeticallyBySortedLetters() {
        val anagrams = listOf(
            AnagramMetadata("ABC", 1),
            AnagramMetadata("TESTS", 1),
            AnagramMetadata("AB", 2),
            AnagramMetadata("AC", 1),
            AnagramMetadata("CASES", 1)
        )

        val expected = listOf(
            AnagramMetadata("CASES", 1),
            AnagramMetadata("TESTS", 1),
            AnagramMetadata("ABC", 1),
            AnagramMetadata("AB", 2),
            AnagramMetadata("AC", 1)
        )

        assertEquals(expected, anagrams.sorted())
    }
}
