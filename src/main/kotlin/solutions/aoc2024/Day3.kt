package solutions.aoc2024

import solutions.utils.AoCProblem

fun main() {
    Day3().runAllSolutions()
}

class Day3: AoCProblem() {
    override val testInput: List<String>
        get() = listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")

    fun solution1(input: List<String>) {
        val mulRegex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")

        val sum: Long = mulRegex.findAll(input.joinToString("")).sumOf { matchResult ->
            matchResult.value.removePrefix("mul(").removeSuffix(")").split(",").let { digits ->
                digits[0].toLong() * digits[1].toLong()
            }
        }

        println("Sum of mul(X,Y) commands: $sum")
    }

    fun solution2(input: List<String>) {
        val mulRegex = Regex("(do\\(\\))|(don't\\(\\))|(mul\\(\\d{1,3},\\d{1,3}\\))")

        var skipNextMul = false
        val sum: Long = mulRegex.findAll(input.joinToString("")).sumOf { matchResult ->
            when (matchResult.value) {
                "do()" -> {
                    skipNextMul = false
                    0L
                }
                "don't()" -> {
                    skipNextMul = true
                    0L
                }
                else -> {
                    if (skipNextMul) {
                        0L
                    } else {
                        matchResult.value.removePrefix("mul(").removeSuffix(")").split(",").let { digits ->
                            digits[0].toLong() * digits[1].toLong()
                        }
                    }
                }
            }
        }

        println("Sum of mul(X,Y) commands: $sum")
    }
}