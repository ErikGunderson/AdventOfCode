package solutions.aoc2020

import solutions.utils.AoCProblem

class Day3 : AoCProblem() {
    fun solution1(input: List<String>) {
        val treeChar = '#'

        var treeCount = 0
        var arrayIndex = 0

        input.forEach { singleInput ->
            if (singleInput[arrayIndex] == treeChar) treeCount += 1

            arrayIndex = (arrayIndex + 3) % singleInput.length
        }

        print("Encountered tree count is $treeCount")
        print("\nDONE :D")
    }

    fun solution2(input: List<String>) {
        val treeChar = '#'

        var slopeOneTreeCount = 0
        var slopeTwoTreeCount = 0
        var slopeThreeTreeCount = 0
        var slopeFourTreeCount = 0
        var slopeFiveTreeCount = 0

        input.forEachIndexed { index, singleInput ->
            if (index == 0) return@forEachIndexed

            if (singleInput[index % singleInput.length] == treeChar) slopeOneTreeCount += 1
            if (singleInput[(index * 3) % singleInput.length] == treeChar) slopeTwoTreeCount += 1
            if (singleInput[(index * 5) % singleInput.length] == treeChar) slopeThreeTreeCount += 1
            if (singleInput[(index * 7) % singleInput.length] == treeChar) slopeFourTreeCount += 1

            if (index % 2 == 0) {
                if (singleInput[(index / 2) % singleInput.length] == treeChar) slopeFiveTreeCount += 1
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