package anagramfinder

class ErrorManager {
    fun run(block: () -> Unit) {
        try {
            block()
        } catch (e: Exception) {
            if (e is IllegalArgumentException) {
                System.err.println(e.localizedMessage)
            } else {
                System.err.println(e)
            }
        }
    }
}
