package solutions.aoc2024

import solutions.utils.AoCProblem
import kotlin.math.absoluteValue

fun main() {
    Day1().runAllSolutions()
}

class Day1: AoCProblem() {
    fun solution1(input: List<String>) {
        val left = mutableListOf<Long>()
        val right = mutableListOf<Long>()

        input.forEach { line -> line.split("   ").let {
            left.add(it[0].toLong())
            right.add(it[1].toLong())
        }}

        left.sort()
        right.sort()

        var locationIdDiffSum: Long = 0

        left.forEachIndexed { index, locationId ->
            locationIdDiffSum += (locationId - right[index]).absoluteValue
        }

        println("Location ID differences summed: $locationIdDiffSum")
    }

    fun solution2(input: List<String>) {
        val left = mutableListOf<Long>()
        val right = mutableMapOf<Long, Int>()

        input.forEach { line -> line.split("   ").let {
            left.add(it[0].toLong())

            it[1].toLong().let { rightVal ->
                if (right.containsKey(rightVal)) {
                    right[rightVal] = right[rightVal]!! + 1
                } else {
                    right[rightVal] = 1
                }
            }
        }}

        var similarityScoreSum: Long = 0
        left.forEach { leftVal ->
            similarityScoreSum += leftVal * (right[leftVal] ?: 0)
        }

        println("Similarity scores summed: $similarityScoreSum")
    }
}