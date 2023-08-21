package anagramfinder

import org.junit.Test
import kotlin.test.assertEquals

class GraphFormatterTest {
    @Test
    fun shouldFormatSimple() {
        val data = setOf(
            AnagramConnection("ABC", 1, 4) to setOf(
                AnagramConnection("AB", 2, 2),
                AnagramConnection("AC", 1, 1)
            ),
            AnagramConnection("TEST", 1, 1) to emptySet()
        )

        val expected = """
            digraph {
                "ABC\n1 - 4" -> {"AB\n2 - 2", "AC\n1 - 1"}
                "TEST\n1 - 1"
            }
        """.trimIndent()

        val result = GraphFormatter().format(data)

        assertEquals(expected, result)
    }

    @Test
    fun shouldFormatWithoutSkipping() {
        val data = setOf(
            AnagramConnection("ABBCC", 1, 7) to setOf(
                AnagramConnection("ABBC", 2, 5),
                AnagramConnection("ACC", 1, 1)
            ),
            AnagramConnection("ABBC", 2, 5) to setOf(
                AnagramConnection("ABC", 3, 3)
            ),
            AnagramConnection("AABC", 1, 4) to setOf(
                AnagramConnection("ABC", 3, 3)
            )
        )

        val expected = """
            digraph {
                "ABBCC\n1 - 7" -> {"ABBC\n2 - 5", "ACC\n1 - 1"}
                "ABBC\n2 - 5" -> "ABC\n3 - 3"
                "AABC\n1 - 4" -> "ABC\n3 - 3"
            }
        """.trimIndent()

        val result = GraphFormatter().format(data)

        assertEquals(expected, result)
    }

    @Test
    fun shouldFormatWithoutDoubleCountNorSkipping() {
        val data = setOf(
            AnagramConnection("EESTT", 2, 15) to setOf(
                AnagramConnection("EEST", 3, 9),
                AnagramConnection("ESTT", 2, 7),
                AnagramConnection("EETT", 1, 4)
            ),
            AnagramConnection("EEST", 3, 9) to setOf(
                AnagramConnection("EET", 2, 2),
                AnagramConnection("EST", 4, 4)
            ),
            AnagramConnection("ESTT", 2, 7) to setOf(
                AnagramConnection("EST", 4, 4),
                AnagramConnection("ETT", 1, 1)
            ),
            AnagramConnection("EETT", 1, 4) to setOf(
                AnagramConnection("EET", 2, 2),
                AnagramConnection("ETT", 1, 1)
            )
        )

        val expected = """
            digraph {
                "EESTT\n2 - 15" -> {"EEST\n3 - 9", "ESTT\n2 - 7", "EETT\n1 - 4"}
                "EEST\n3 - 9" -> {"EET\n2 - 2", "EST\n4 - 4"}
                "ESTT\n2 - 7" -> {"EST\n4 - 4", "ETT\n1 - 1"}
                "EETT\n1 - 4" -> {"EET\n2 - 2", "ETT\n1 - 1"}
            }
        """.trimIndent()

        val result = GraphFormatter().format(data)

        assertEquals(expected, result)
    }

    @Test
    fun shouldFormatWithExamples() {
        val data = setOf(
            AnagramConnection("TESTE", 2, 15) to setOf(
                AnagramConnection("ETES", 3, 9),
                AnagramConnection("TEST", 2, 7),
                AnagramConnection("TETE", 1, 4)
            ),
            AnagramConnection("ETES", 3, 9) to setOf(
                AnagramConnection("ETE", 2, 2),
                AnagramConnection("TES", 4, 4),
            ),
            AnagramConnection("TEST", 2, 7) to setOf(
                AnagramConnection("TES", 4, 4),
                AnagramConnection("TET", 1, 1),
            ),
            AnagramConnection("TETE", 1, 4) to setOf(
                AnagramConnection("ETE", 2, 2),
                AnagramConnection("TET", 1, 1)
            )
        )

        val expected = """
            digraph {
                "TESTE\n2 - 15" -> {"ETES\n3 - 9", "TEST\n2 - 7", "TETE\n1 - 4"}
                "ETES\n3 - 9" -> {"ETE\n2 - 2", "TES\n4 - 4"}
                "TEST\n2 - 7" -> {"TES\n4 - 4", "TET\n1 - 1"}
                "TETE\n1 - 4" -> {"ETE\n2 - 2", "TET\n1 - 1"}
            }
        """.trimIndent()

        val result = GraphFormatter().format(data)

        assertEquals(expected, result)
    }
}
