package solutions.aoc2020

import AoC2020Problem

class Day3 : AoC2020Problem() {
    override fun solution1() {
        val treeChar = '#'

        var treeCount = 0
        var arrayIndex = 0

        inputFile.readLines().map { it.trim() }.forEach { input ->
            if (input[arrayIndex] == treeChar) treeCount += 1

            arrayIndex = (arrayIndex + 3) % input.length
        }

        print("Encountered tree count is $treeCount")
        print("\nDONE :D")
    }

    override fun solution2() {
        val treeChar = '#'

        var slopeOneTreeCount = 0
        var slopeTwoTreeCount = 0
        var slopeThreeTreeCount = 0
        var slopeFourTreeCount = 0
        var slopeFiveTreeCount = 0

        inputFile.readLines().map { it.trim() }.forEachIndexed { index, input ->
            if (index == 0) return@forEachIndexed

            if (input[index % input.length] == treeChar) slopeOneTreeCount += 1
            if (input[(index * 3) % input.length] == treeChar) slopeTwoTreeCount += 1
            if (input[(index * 5) % input.length] == treeChar) slopeThreeTreeCount += 1
            if (input[(index * 7) % input.length] == treeChar) slopeFourTreeCount += 1

            if (index % 2 == 0) {
                if (input[(index / 2) % input.length] == treeChar) slopeFiveTreeCount += 1
            }
        }

        val multiplied =
            slopeOneTreeCount.toLong() * slopeTwoTreeCount * slopeThreeTreeCount * slopeFourTreeCount * slopeFiveTreeCount

        print("Slope 1 Count: $slopeOneTreeCount\n")
        print("Slope 2 Count: $slopeTwoTreeCount\n")
        print("Slope 3 Count: $slopeThreeTreeCount\n")
        print("Slope 4 Count: $slopeFourTreeCount\n")
        print("Slope 5 Count: $slopeFiveTreeCount\n")
        print("Multiplied together: $multiplied\n")
        print("\nDONE :D")
    }
}