package anagramfinder

class GraphFormatter(
    private val graphHeader: String = "digraph {",
    private val indent: String = "    ",
    private val nodeFormat: String = "\"%s\\n%d - %d\"",
    private val link: String = " -> ",
    private val subgraphOpenDelimiter: String = "{",
    private val subgraphItemDelimiter: String = ", ",
    private val subgraphCloseDelimiter: String = "}",
    private val graphFooter: String = "}"
) {
    fun format(data: Set<Pair<AnagramConnection, Set<AnagramConnection>>>): String {
        val lines = mutableListOf(graphHeader)
        data.forEach {
            lines.add(indent + formatPair(it.first, it.second))
        }
        lines.add(graphFooter)
        return lines.joinToString("\n")
    }

    private fun formatPair(parent: AnagramConnection, children: Set<AnagramConnection>): String {
        if (children.isEmpty()) return formatNode(parent)
        if (children.size == 1) return "${formatNode(parent)}${link}${formatNode(children.first())}"
        return "${formatNode(parent)}${link}${subgraphOpenDelimiter}${
            children.joinToString(subgraphItemDelimiter, transform = ::formatNode)
        }$subgraphCloseDelimiter"
    }

    private fun formatNode(node: AnagramConnection): String {
        return nodeFormat.format(node.name, node.ownAnagrams, node.compoundAnagrams)
    }
}
