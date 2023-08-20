package anagramfinder

import kotlin.test.Test
import kotlin.test.assertEquals

class GraphRelationshipsComputerTest {
    @Test
    fun shouldComputeSimple() {
        val anagrams = listOf(
            AnagramMetadata("AB", 2),
            AnagramMetadata("AC", 1),
            AnagramMetadata("ABC", 1),
            AnagramMetadata("TEST", 1)
        )

        val expected = setOf(
            AnagramConnection("ABC", 1, 4) to AnagramConnection("AB", 2, 2),
            AnagramConnection("ABC", 1, 4) to AnagramConnection("AC", 1, 1),
            AnagramConnection("TEST", 1, 1) to null
        )

        val result = GraphRelationshipsComputer().compute(anagrams)

        assertEquals(expected, result)
    }

    @Test
    fun shouldComputeWithoutSkipping() {
        val anagrams = listOf(
            AnagramMetadata("ABC", 3),
            AnagramMetadata("ACC", 1),
            AnagramMetadata("AABC", 1),
            AnagramMetadata("ABBC", 2),
            AnagramMetadata("ABBCC", 1),
        )

        val expected = setOf(
            AnagramConnection("ABBCC", 1, 7) to AnagramConnection("ABBC", 2, 5),
            AnagramConnection("ABBCC", 1, 7) to AnagramConnection("ACC", 1, 1),
            AnagramConnection("ABBC", 2, 5) to AnagramConnection("ABC", 3, 3),
            AnagramConnection("AABC", 1, 4) to AnagramConnection("ABC", 3, 3)
        )

        val result = GraphRelationshipsComputer().compute(anagrams)

        assertEquals(expected, result)
    }

    @Test
    fun shouldComputeWithoutDoubleCountNorSkipping() {
        val anagrams = listOf(
            AnagramMetadata("EESTT", 2),
            AnagramMetadata("EEST", 3),
            AnagramMetadata("ESTT", 2),
            AnagramMetadata("EETT", 1),
            AnagramMetadata("EET", 2),
            AnagramMetadata("EST", 4),
            AnagramMetadata("ETT", 1),
        )

        val expected = setOf(
            AnagramConnection("EESTT", 2, 15) to AnagramConnection("EEST", 3, 9),
            AnagramConnection("EESTT", 2, 15) to AnagramConnection("ESTT", 2, 7),
            AnagramConnection("EESTT", 2, 15) to AnagramConnection("EETT", 1, 4),
            AnagramConnection("EEST", 3, 9) to AnagramConnection("EET", 2, 2),
            AnagramConnection("EEST", 3, 9) to AnagramConnection("EST", 4, 4),
            AnagramConnection("ESTT", 2, 7) to AnagramConnection("EST", 4, 4),
            AnagramConnection("ESTT", 2, 7) to AnagramConnection("ETT", 1, 1),
            AnagramConnection("EETT", 1, 4) to AnagramConnection("EET", 2, 2),
            AnagramConnection("EETT", 1, 4) to AnagramConnection("ETT", 1, 1)
        )

        val result = GraphRelationshipsComputer().compute(anagrams)

        assertEquals(expected, result)
    }

    @Test
    fun shouldComputeWithExamples() {
        val anagrams = listOf(
            AnagramMetadata("EESTT", 2, "TESTE"),
            AnagramMetadata("EEST", 3, "ETES"),
            AnagramMetadata("ESTT", 2, "TEST"),
            AnagramMetadata("EETT", 1, "TETE"),
            AnagramMetadata("EET", 2, "ETE"),
            AnagramMetadata("EST", 4, "TES"),
            AnagramMetadata("ETT", 1, "TET"),
        )

        val expected = setOf(
            AnagramConnection("TESTE", 2, 15) to AnagramConnection("ETES", 3, 9),
            AnagramConnection("TESTE", 2, 15) to AnagramConnection("TEST", 2, 7),
            AnagramConnection("TESTE", 2, 15) to AnagramConnection("TETE", 1, 4),
            AnagramConnection("ETES", 3, 9) to AnagramConnection("ETE", 2, 2),
            AnagramConnection("ETES", 3, 9) to AnagramConnection("TES", 4, 4),
            AnagramConnection("TEST", 2, 7) to AnagramConnection("TES", 4, 4),
            AnagramConnection("TEST", 2, 7) to AnagramConnection("TET", 1, 1),
            AnagramConnection("TETE", 1, 4) to AnagramConnection("ETE", 2, 2),
            AnagramConnection("TETE", 1, 4) to AnagramConnection("TET", 1, 1)
        )

        val result = GraphRelationshipsComputer().compute(anagrams)

        assertEquals(expected, result)
    }
}
