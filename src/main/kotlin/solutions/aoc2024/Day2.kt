package solutions.aoc2024

import solutions.utils.AoCProblem

fun main() {
    Day2().runAllSolutions()
}

class Day2: AoCProblem() {
    fun solution1(input: List<String>) {
        var safeReportCount = 0

        input.forEach { line -> line.split(" ").map { it.toInt() }.let { reactorReport ->
            val reactorLevelTrend = if (reactorReport[0] - reactorReport[1] > 0) {
                ReactorLevelTrend.DECREASING
            } else ReactorLevelTrend.INCREASING

            var safeReport = true
            reactorReport.windowed(2).map { (left, right) -> left - right }.forEach { pairDiff ->
                if (pairDiff == 0
                    || (reactorLevelTrend == ReactorLevelTrend.DECREASING && pairDiff !in 1..3)
                    || (reactorLevelTrend == ReactorLevelTrend.INCREASING && pairDiff !in -3..-1)) {
                     safeReport = false
                }
            }

            if (safeReport) safeReportCount++
        }}

        println("Safe Report Count: $safeReportCount")
    }

    fun solution2(input: List<String>) {
        var safeReportCount = 0

        input.forEach { line -> line.split(" ").map { it.toInt() }.let { reactorReport ->
            val reactorLevelTrend = if (reactorReport[0] - reactorReport[1] > 0) {
                ReactorLevelTrend.DECREASING
            } else ReactorLevelTrend.INCREASING

            var badPairCount = 0
            reactorReport.windowed(2).map { (left, right) -> left - right }.forEach { pairDiff ->
                if (pairDiff == 0
                    || (reactorLevelTrend == ReactorLevelTrend.DECREASING && pairDiff !in 1..3)
                    || (reactorLevelTrend == ReactorLevelTrend.INCREASING && pairDiff !in -3..-1)) {
                    badPairCount++
                }
            }

            if (badPairCount in 0..1) safeReportCount++
        }}

        println("Safe Report Count: $safeReportCount")
    }
}

enum class ReactorLevelTrend {
    INCREASING, DECREASING
}