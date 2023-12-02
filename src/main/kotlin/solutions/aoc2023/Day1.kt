package solutions.aoc2023

import solutions.utils.AoCProblem
import kotlin.math.max

fun main() {
    Day1().runAllSolutions()
}

class Day1: AoCProblem() {
    fun solution1(input: List<String>) {
        print("Calibration sum is ${findCalibrationSum(input)}")
    }

    //IMPORTANT NOTE: line 916 of input
    //twolxzdhfourqjeightfour55zjvconeightnf
    //                             ^^^^^^^
    //Can't use standard library Regex.replace, doesn't handle overlaps
    fun solution2(input: List<String>) {
        val modifiedInput = input.map { row ->
            var currentIndex = 0
            var modifiedRow = ""

            var foundMatch = true
            while (foundMatch) {
                Regex("(one|two|three|four|five|six|seven|eight|nine)").find(row, max(currentIndex - 1, 0))?.let { match ->
                    if (currentIndex <= match.range.first) modifiedRow += row.substring(currentIndex, match.range.first)
                    currentIndex = match.range.last + 1
                    modifiedRow += match.value.convertDigit()
                } ?: run {
                    foundMatch = false
                    modifiedRow += row.substring(currentIndex, row.length)
                }
            }

            modifiedRow
        }

        print("Calibration sum is ${findCalibrationSum(modifiedInput)}")
    }

    private fun findCalibrationSum(input: List<String>): Int {
        var calibrationSum = 0

        input.forEach { row ->
            row.filter { it.isDigit() }.let { digits ->
                calibrationSum += (digits.first().digitToInt() * 10) + digits.last().digitToInt()
            }
        }
        return calibrationSum
    }

    private fun String.convertDigit(): String {
        return when(this) {
            "one" -> "1"
            "two" -> "2"
            "three" -> "3"
            "four" -> "4"
            "five" -> "5"
            "six" -> "6"
            "seven" -> "7"
            "eight" -> "8"
            "nine" -> "9"
            else -> throw RuntimeException("BAD NUMBER >:[]")
        }
    }
}