package solutions.aoc2020

import solutions.utils.AoCProblem

class Day6 : AoCProblem() {
    fun solution1(input: List<String>) {
        val currentGroupYesQuestions = mutableListOf<Char>()
        val yesCounts = mutableListOf<Int>()

        input.forEachIndexed { index, inputLine ->
            if (inputLine != "") {
                currentGroupYesQuestions.addAll(inputLine.toList())
            }

            if (index == input.lastIndex || inputLine == "") {
                yesCounts.add(currentGroupYesQuestions.distinct().size)
                currentGroupYesQuestions.clear()
            }
        }

        print("Total yes count is ${yesCounts.sum()}")
        print("\nDONE :D")
    }

    fun solution2(input: List<String>) {
        val currentGroupYesQuestions = mutableListOf<List<Char>>()
        val yesCounts = mutableListOf<Int>()

        input.forEachIndexed { index, inputLine ->
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

        print("Total yes count is ${yesCounts.sum()}")
        print("\nDONE :D")
    }
}