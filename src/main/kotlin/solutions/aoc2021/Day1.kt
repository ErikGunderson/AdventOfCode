package solutions.aoc2021

import AoC2021Problem

class Day1: AoC2021Problem() {
    override fun solution1(input: List<String>) {
        var incCount = 0
        input.map { it.toInt() }
            .windowed(2)
            .forEach { if (it[1] > it[0]) incCount++ }

        print("Depth grew $incCount times")
    }

    override fun solution2(input: List<String>) {
        var incCount = 0
        input.map { it.toInt() }
            .windowed(3) { it[0] + it[1] + it[2] }
            .windowed(2)
            .forEach { if (it[1] > it[0]) incCount++ }

        print("Sliding window depth grew $incCount times")
    }
}