package solutions.aoc2021

import AoC2021Problem

class Day1: AoC2021Problem() {
    fun solution1() {
        var incCount = 0
        inputFile.readLines().map { it.trim() }.map { it.toInt() }
            .windowed(2)
            .forEach { if (it[1] > it[0]) incCount++ }

        print("Depth grew $incCount times")
        print("\nDONE :D")
    }

    fun solution2() {
        var incCount = 0
        inputFile.readLines().map { it.trim() }.map { it.toInt() }
            .windowed(3) { it[0] + it[1] + it[2] }
            .windowed(2)
            .forEach { if (it[1] > it[0]) incCount++ }

        print("Sliding window depth grew $incCount times")
        print("\nDONE :D")
    }
}