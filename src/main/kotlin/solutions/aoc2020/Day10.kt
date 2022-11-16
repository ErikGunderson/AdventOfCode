package solutions.aoc2020

import solutions.utils.AoCProblem

class Day10 : AoCProblem() {
    fun solution1(input: List<String>) {
        var oneJoltDiffs = 0
        var threeJoltDiffs = 1

        input.map { it.toInt() }.sorted().let {
            it.zipWithNext { current, next ->
                when (next - current) {
                    1 -> oneJoltDiffs += 1
                    3 -> threeJoltDiffs += 1
                }
            }

            when (it[0] - 0) {
                1 -> oneJoltDiffs += 1
                3 -> threeJoltDiffs += 1
            }
        }

        print("1 Jolt diffs: $oneJoltDiffs; 3 Jolt diffs: $threeJoltDiffs; multiplied is: ${oneJoltDiffs * threeJoltDiffs}")
        print("\nDONE :D")
    }

    fun solution2(input: List<String>) {
        input.map { Adapter(it.toInt()) }
            .toMutableList()
            .apply { add(Adapter(0)) }
            .apply { add(Adapter(maxOf { it.jolts } + 3, possibleExitConnections = 1)) }
            .sortedByDescending { it.jolts }
            .let { inputSet ->
                inputSet.forEachIndexed { index, adapter ->
                    var continueLoop = true
                    var currentIndexOffset = 1

                    while (continueLoop) {
                        val currentIndex = index - currentIndexOffset

                        if (currentIndex < 0) {
                            continueLoop = false
                        } else {
                            val currentJoltDiff = inputSet[currentIndex].jolts - adapter.jolts

                            if (currentJoltDiff in 1..3) {
                                adapter.possibleExitConnections += inputSet[currentIndex].possibleExitConnections
                                currentIndexOffset += 1
                            } else continueLoop = false
                        }
                    }
                }

                print("\ntotal adapter combinations: ${inputSet[inputSet.lastIndex].possibleExitConnections}")
                print("\nDONE :D")
            }
    }
}

data class Adapter(
    val jolts: Int,
    var possibleExitConnections: Long = 0
)