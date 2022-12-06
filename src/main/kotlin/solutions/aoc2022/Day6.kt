package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day6().runAllSolutions()
}

class Day6 : AoCProblem() {
    fun solution1(input: List<String>) {
        print("Start-of-packet last char is at position ${scanStringForUniqueSubstring(input.first(), 4)}")
    }

    fun solution2(input: List<String>) {
        print("Start-of-message last char is at position ${scanStringForUniqueSubstring(input.first(), 14)}")
    }

    private fun scanStringForUniqueSubstring(input: String, substringLength: Int) : Int {
        input.windowed(substringLength, transform = { it.toList() }).run scanWindows@ {
            forEachIndexed { index, window ->
                if (window.distinct().size == substringLength) {
                    return index + substringLength
                }
            }
        }

        return -1
    }
}