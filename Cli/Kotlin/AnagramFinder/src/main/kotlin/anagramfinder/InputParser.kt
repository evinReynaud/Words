package anagramfinder

import java.io.BufferedReader

class InputParser {
    companion object {
        fun parse(reader: BufferedReader): List<AnagramMetadata> {
            var lineIndex = 1
            return reader.lines().map { line -> parseLine(lineIndex++, line) }.toList()
        }

        private fun parseLine(lineIndex: Int, line: String): AnagramMetadata =
            line.split(" ").filter { it.isNotEmpty() }
                .let { blocs ->
                    if (blocs.size != 2)
                        throw IllegalArgumentException("Could not parse line $lineIndex")
                    var (value, anagram) = Pair(blocs[0].toIntOrNull(), blocs[1])
                    if (value == null) {
                        value = blocs[1].toIntOrNull()
                        anagram = blocs[0]
                    }
                    if (value == null)
                        throw IllegalArgumentException("Could not parse line $lineIndex")
                    AnagramMetadata(anagram, value)
                }
    }
}
