package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day3().runAllSolutions()
}

class Day3: AoCProblem() {
    fun solution1(input: List<String>) {
        val prioritySum = input.sumOf { rucksackContents ->
            rucksackContents.chunked(rucksackContents.length / 2).map { it.toSet() }
                .let { (firstComp, secondComp) -> firstComp.intersect(secondComp) }
                .first()
                .convertChar()
        }

        print("Priority sum: $prioritySum")
    }

    fun solution2(input: List<String>) {
        val prioritySum = input.chunked(3).sumOf { groupRucksackContents ->
            groupRucksackContents.map { it.toSet() }
                .let { (firstBag, secondBag, thirdBag) -> firstBag.intersect(secondBag).intersect(thirdBag) }
                .first()
                .convertChar()
        }

        print("Priority sum of badges: $prioritySum")
    }

    /**
     * If lowercase, subtract 96 from the code to get a-z into the range 1-26
     * If uppercase, subtract 38 from the code to get A-Z into the range 27-52
     */
    private fun Char.convertChar() = if (isLowerCase()) code - 96 else code - 38
}