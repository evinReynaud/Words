package anagramfinder

import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException

class FileUtils {
    companion object {
        fun readFile(filename: String): BufferedReader {
            try {
                return File(filename).bufferedReader()
            } catch (e: FileNotFoundException) {
                throw IllegalArgumentException("File not found: $filename")
            }
        }
        fun writeToFile(filename: String, it: String) {
            try {
                File(filename).writeText(it)
            } catch (e: FileNotFoundException) {
                throw IllegalArgumentException("File or folder not found: $filename")
            }
        }
    }
}
