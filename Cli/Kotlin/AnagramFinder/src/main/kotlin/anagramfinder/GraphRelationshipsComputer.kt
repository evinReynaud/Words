package anagramfinder

class GraphRelationshipsComputer {

    companion object {
        fun compute(anagrams: List<AnagramMetadata>): Set<Pair<AnagramConnection, Set<AnagramConnection>>> {
            // anagrams are sorted small first, then alphabetically
            val sortedAnagrams = anagrams.sorted()
            val relationshipMap = buildRelationshipMap(sortedAnagrams)

            val computedMap = computeMapCompoundAnagramsNumber(relationshipMap)

            return computePairs(computedMap)
        }

        private fun buildRelationshipMap(anagrams: List<AnagramMetadata>): Map<String, AnagramLink> {
            val map = mutableMapOf<String, AnagramLink>()

            anagrams.sorted().forEach { anagram ->
                // For each anagram, if it has one or many parents, attach it to them, otherwise create a new entry
                // But do not attach to a grandparent if we have a parent
                map.keys.filter { key -> anagram.sortedLetters.isIncludedIn(key) }
                    .takeIf { parentKeys -> parentKeys.isNotEmpty() }?.forEach { parentKey ->
                        // We have found a parent
                        // We need to find where anagram goes in the tree (multiple spots possible)
                        val parent = map[parentKey]!!
                        map.replace(parentKey, parent.copy(children = anagram.addAsDescendant(parent)))
                    } ?: map.put(anagram.sortedLetters, AnagramLink(anagram, emptyList()))
            }
            return map
        }

        private fun AnagramMetadata.addAsDescendant(link: AnagramLink): List<AnagramLink> {
            val (matchingChildren, otherChildren) = link.children.partition { child ->
                this.sortedLetters.isIncludedIn(
                    child.anagram.sortedLetters
                )
            }
            return if (matchingChildren.isEmpty()) {
                // The anagram is a direct child
                otherChildren.plus(AnagramLink(this, emptyList()))
            } else {
                matchingChildren.map { it.copy(children = this.addAsDescendant(it)) }.plus(otherChildren)
            }
        }

        // Returns true if all characters of this are included in s (one to one)
        // Both values' letters are expected to be sorted alphabetically
        private fun String.isIncludedIn(s: String): Boolean {
            if (s.length < this.length) {
                return false
            }
            var j = 0
            var i = 0
            while (i < this.length && j < s.length) {
                if (this[i] < s[j]) return false
                if (this[i] == s[j]) i++
                j++
            }
            return i == this.length
        }

        // Could probably be optimized
        private fun computeMapCompoundAnagramsNumber(relationshipMap: Map<String, AnagramLink>): Map<String, AnagramConnectionLink> =
            relationshipMap.map { (key, value) ->
                key to computeCompoundForSelfAndChildren(value)
            }.toMap()

        private fun computeCompoundForSelfAndChildren(link: AnagramLink): AnagramConnectionLink = AnagramConnectionLink(
            link.anagram.example ?: link.anagram.letters,
            link.anagram.sortedLetters,
            link.anagram.anagramCount,
            getChildrenValues(link).fold(0) { acc, (_, v) -> acc + v },
            link.children.map(::computeCompoundForSelfAndChildren)
        )

        private fun getChildrenValues(link: AnagramLink): Set<Pair<String, Int>> =
            setOf(Pair(link.anagram.sortedLetters, link.anagram.anagramCount))
                .plus(link.children.flatMap(::getChildrenValues))

        private fun computePairs(
            computedMap: Map<String, AnagramConnectionLink>
        ): Set<Pair<AnagramConnection, Set<AnagramConnection>>> =
            computedMap.flatMap { (_, anagram) -> computePairsForLink(anagram, false) }.toSet()

        private fun computePairsForLink(
            link: AnagramConnectionLink,
            isChild: Boolean
        ): Set<Pair<AnagramConnection, Set<AnagramConnection>>> =
            link.children.takeUnless { it.isEmpty() }
                ?.let { children ->
                    setOf(AnagramConnection(link.name, link.ownAnagrams, link.compoundAnagrams) to
                            children.map { AnagramConnection(it.name, it.ownAnagrams, it.compoundAnagrams) }.toSet()
                    )
                        .plus(children.flatMap { computePairsForLink(it, true) }.toSet())
                }
                ?: if (isChild) emptySet() else setOf(
                    AnagramConnection(link.name, link.ownAnagrams, link.compoundAnagrams) to emptySet()
                )
    }

    private data class AnagramLink(
        val anagram: AnagramMetadata,
        val children: List<AnagramLink>
    )

    private data class AnagramConnectionLink(
        val name: String,
        val sortedLetters: String,
        val ownAnagrams: Int,
        val compoundAnagrams: Int,
        val children: List<AnagramConnectionLink>
    )
}
