package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day4().runAllSolutions()
}

class Day4: AoCProblem() {
    fun solution1(input: List<String>) {
        val containedPairs = input.count { assignedPair -> assignedPair.split(',')
            .map { it.split('-').let { (rangeStart, rangeEnd) -> rangeStart.toInt()..rangeEnd.toInt() } }
            .let { (firstElf, secondElf) -> firstElf.containsRange(secondElf) || secondElf.containsRange(firstElf) }
        }

        print("Count of fully contained pairs: $containedPairs")
    }

    fun solution2(input: List<String>) {
        val containedPairs = input.count { assignedPair -> assignedPair.split(',')
            .map { it.split('-').let { (rangeStart, rangeEnd) -> rangeStart.toInt()..rangeEnd.toInt() } }
            .let { (firstElf, secondElf) -> firstElf.overlapsRange(secondElf) || secondElf.overlapsRange(firstElf) }
        }

        print("Count of overlapping pairs: $containedPairs")
    }

    private fun IntRange.containsRange(other: IntRange) = other.first >= this.first && other.last <= this.last

    private fun IntRange.overlapsRange(other: IntRange) = this.contains(other.first) || this.contains(other.last)
}