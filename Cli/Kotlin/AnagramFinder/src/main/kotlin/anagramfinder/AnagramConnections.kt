package anagramfinder

data class AnagramConnection (
    val name: String,
    val ownAnagrams: Int,
    val compoundAnagrams: Int
)
