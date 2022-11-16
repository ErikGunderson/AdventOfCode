package solutions.aoc2021

import solutions.utils.AoCProblem
import kotlin.math.*

fun main() {
    Day7().runAllSolutions()
}

class Day7 : AoCProblem() {

    fun solution1(input: List<String>) {
        input[0].split(",").map { it.toInt() }.sorted().let { inputs ->
            val median = inputs.median()
            val totalFuel = inputs.sumOf { abs(it - median) }

            print("Total fuel expenditure at minimum: $totalFuel")
        }
    }

    fun solution2(input: List<String>) {
        input[0].split(",").map { it.toInt() }.let { inputs ->
            val avgPair = inputs.avg().let { floor(it).toInt() to ceil(inputs.avg()).toInt() }
            val totalFuel = min(
                inputs.sumOf { abs(it - avgPair.first).gaussSum() },
                inputs.sumOf { abs(it - avgPair.second).gaussSum() },
            )

            print("Total fuel expenditure at minimum: $totalFuel")
        }
    }
}

/**
 * NOTE: requires value sorted list
 */
fun List<Int>.median(): Int {
    return if (this.lastIndex % 2 == 0) {
        this[this.lastIndex / 2]
    } else {
        (this.lastIndex / 2.toDouble()).let {
            val leftIndex = floor(it).toInt()
            val rightIndex = ceil(it).toInt()

            (this[leftIndex] + this[rightIndex]) / 2
        }
    }
}

fun List<Int>.avg(): Float {
    return (this.sum() / this.size.toFloat())
}

fun Int.gaussSum() = this * (this + 1) / 2