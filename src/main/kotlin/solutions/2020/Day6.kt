package solutions

import FileReader
import java.io.File
import kotlin.math.ceil
import kotlin.math.floor

class Day6 : FileReader() {
    fun solution1() {
        val currentGroupYesQuestions = mutableListOf<Char>()
        val yesCounts = mutableListOf<Int>()

        inputFile.readLines().let { input ->
            input.map { it.trim() }.forEachIndexed { index, inputLine ->
                if (inputLine != "") {
                    currentGroupYesQuestions.addAll(inputLine.toList())
                }

                if (index == input.lastIndex || inputLine == "") {
                    yesCounts.add(currentGroupYesQuestions.distinct().size)
                    currentGroupYesQuestions.clear()
                }
            }
        }

        print("Total yes count is ${yesCounts.sum()}")
        print("\nDONE :D")
    }

    fun solution2() {
        val currentGroupYesQuestions = mutableListOf<List<Char>>()
        val yesCounts = mutableListOf<Int>()

        inputFile.readLines().let { input ->
            input.map { it.trim() }.forEachIndexed { index, inputLine ->
                if (inputLine != "") {
                    currentGroupYesQuestions.add(inputLine.toList())
                }

                if (index == input.lastIndex || inputLine == "") {
                    var intersectQuestions = currentGroupYesQuestions[0]

                    currentGroupYesQuestions.forEach {
                        intersectQuestions = it.intersect(intersectQuestions).toList()
                    }

                    yesCounts.add(intersectQuestions.size)
                    currentGroupYesQuestions.clear()
                }
            }
        }

        print("Total yes count is ${yesCounts.sum()}")
        print("\nDONE :D")
    }
}