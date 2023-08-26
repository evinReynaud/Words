package anagramfinder.models

data class AnagramMetadata(
    val letters: String,
    val anagramCount: Int,
    val example: String? = letters
): Comparable<AnagramMetadata> {
    val sortedLetters = letters.toCharArray().sorted().joinToString("")

    // Compare this AnagramMetadata to another, ordering them by number of letters (larger first),
    // then alphabetically according to their sorted letters
    override fun compareTo(other: AnagramMetadata): Int = when {
        this.sortedLetters.length > other.sortedLetters.length -> -1
        this.sortedLetters.length < other.sortedLetters.length -> 1
        else -> this.sortedLetters.compareTo(other.sortedLetters)
    }
}
