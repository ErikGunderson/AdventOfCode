package solutions.aoc2021

import solutions.utils.AoCProblem

class Day1: AoCProblem() {
    fun solution1(input: List<String>) {
        var incCount = 0
        input.map { it.toInt() }
            .windowed(2)
            .forEach { if (it[1] > it[0]) incCount++ }

        print("Depth grew $incCount times")
    }

    fun solution2(input: List<String>) {
        var incCount = 0
        input.map { it.toInt() }
            .windowed(3) { it[0] + it[1] + it[2] }
            .windowed(2)
            .forEach { if (it[1] > it[0]) incCount++ }

        print("Sliding window depth grew $incCount times")
    }
}