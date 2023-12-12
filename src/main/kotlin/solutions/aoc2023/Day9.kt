package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day9().runAllSolutions()
}

class Day9: AoCProblem() {
    override val testInput: List<String>
        get() = listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45"
        )

    fun solution1(input: List<String>) {
        val extrapolatedLastValues = input.map {
            val lastValueStack = mutableListOf<Int>()
            var currentIteration = it.split(" ").map { it.toInt() }

            lastValueStack.add(currentIteration.last())

            while (currentIteration.any { it != 0 }) {
                currentIteration = currentIteration.windowed(2, 1).map { window ->
                    window[1] - window[0]
                }

                lastValueStack.add(currentIteration.last())
            }

            lastValueStack.sum()
        }

        println("Sum of extrapolated values is ${extrapolatedLastValues.sum()}")
    }

    fun solution2(input: List<String>) {
        val extrapolatedFirstValues = input.map {
            val firstValueStack = mutableListOf<Int>()
            var currentIteration = it.split(" ").map { it.toInt() }

            firstValueStack.add(currentIteration.first())

            while (currentIteration.any { it != 0 }) {
                currentIteration = currentIteration.windowed(2, 1).map { window ->
                    window[1] - window[0]
                }

                firstValueStack.add(currentIteration.first())
            }

            //calculate first values
            firstValueStack.reversed().reduce { acc, firstValue -> firstValue - acc }
        }

//        println("Extrapolated values is $extrapolatedFirstValues")
        println("Sum of extrapolated values is ${extrapolatedFirstValues.sum()}")
    }
}