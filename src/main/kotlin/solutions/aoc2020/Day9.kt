package solutions.aoc2020

import AoC2020Problem

class Day9: AoC2020Problem() {
    override fun solution1(input: List<String>) {
        input.map { it.toLong() }.let { inputSet ->
            inputSet.forEachIndexed { index, input ->
                if (index < 25) return@forEachIndexed

                if (!checkIfListHasPairsToSum(inputSet.subList(index - 25, index), input)) {
                    print("Invalid number in XMAS sequence is: $input")
                    print("\nDONE :D")
                    return@let
                }
            }
        }
    }

    override fun solution2(input: List<String>) {
        input.map { it.toLong() }.let { inputSet ->
            var invalidInput = 0L
            var invalidInputIndex = 0

            run loop@{
                inputSet.forEachIndexed { index, input ->
                    if (index < 25) return@forEachIndexed

                    if (!checkIfListHasPairsToSum(inputSet.subList(index - 25, index), input)) {
                        invalidInput = input
                        invalidInputIndex = index

                        return@loop
                    }
                }
            }

            inputSet.forEachIndexed { index, _ ->
                if (checkForContiguousSum(inputSet.subList(index, invalidInputIndex), invalidInput)) return
            }
        }
    }

    private fun checkIfListHasPairsToSum(numbers: List<Long>, targetNumber: Long): Boolean {
        numbers.forEach { currentValue ->
            if (numbers.any { it != currentValue && currentValue + it == targetNumber }) return true
        }

        return false
    }

    private fun checkForContiguousSum(numbers: List<Long>, targetNumber: Long): Boolean {
        var counter = 0L
        var smallest = Long.MAX_VALUE
        var largest = 0L

        numbers.forEach {
            counter += it

            if (it < smallest) smallest = it
            if (it > largest) largest = it

            if (counter == targetNumber) {
                print("Encryption Weakness is: ${smallest + largest}")
                print("\nDONE :D")
                return true
            } else if (counter > targetNumber) {
                return false
            }
        }

        return false
    }
}