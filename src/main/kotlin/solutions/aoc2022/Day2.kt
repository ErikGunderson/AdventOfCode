package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day2().runAllSolutions()
}

class Day2: AoCProblem() {
    fun solution1(input: List<String>) {
        val scoreTotal = input.sumOf { it.split(" ").let { (theirs, yours) -> scoreForRoundPt1(theirs, yours) } }

        print("Total score is $scoreTotal")
    }

    fun solution2(input: List<String>) {
        val scoreTotal = input.sumOf { it.split(" ").let { (theirs, yours) -> scoreForRoundPt2(theirs, yours) } }

        print("Total score is $scoreTotal")
    }

    private fun scoreForRoundPt1(theirs: String, yours: String) : Int {
        return when (theirs) {
            "A" -> {                //Theirs: Rock
                when (yours) {
                    "X" -> 1 + 3    //Rock - Tie
                    "Y" -> 2 + 6    //Paper - Win
                    else -> 3 + 0   //Scissors - Lose
                }
            }
            "B" -> {                //Theirs: Paper
                when (yours) {
                    "X" -> 1 + 0    //Rock - Lose
                    "Y" -> 2 + 3    //Paper - Tie
                    else -> 3 + 6   //Scissors - Win
                }
            }
            else -> {               //Theirs: Scissors
                when (yours) {
                    "X" -> 1 + 6    //Rock - Win
                    "Y" -> 2 + 0    //Paper - Lose
                    else -> 3 + 3   //Scissors - Tie
                }
            }
        }
    }

    private fun scoreForRoundPt2(theirs: String, yours: String) : Int {
        return when (theirs) {
            "A" -> {                //Theirs: Rock
                when (yours) {
                    "X" -> 3 + 0    //Scissors - Lose
                    "Y" -> 1 + 3    //Rock - Tie
                    else -> 2 + 6   //Paper - Win
                }
            }
            "B" -> {                //Theirs: Paper
                when (yours) {
                    "X" -> 1 + 0    //Rock - Lose
                    "Y" -> 2 + 3    //Paper - Tie
                    else -> 3 + 6   //Scissors - Win
                }
            }
            else -> {               //Theirs: Scissors
                when (yours) {
                    "X" -> 2 + 0    //Paper - Lose
                    "Y" -> 3 + 3    //Scissors - Tie
                    else -> 1 + 6   //Rock - Win
                }
            }
        }
    }
}