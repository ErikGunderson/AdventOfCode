package solutions.aoc2020

import AoC2020Problem

class Day15 : AoC2020Problem() {
    fun solution1() {
        val startingNumbers = inputFile.readText().split(",").map { it.toInt() }
        val numberMap = mutableMapOf<Int, MutableList<Int>>()

        //configure starting conditions
        startingNumbers.forEachIndexed { index, number ->
            numberMap.addValue(number, index)
        }
        var lastNumber = startingNumbers.last()

        for (index in startingNumbers.size until 2020) {
            val currentNumber = if (numberMap[lastNumber]!!.size == 1) {
                0
            } else {
                numberMap[lastNumber]!!.let { turnsNumberWasSaid ->
                    turnsNumberWasSaid[turnsNumberWasSaid.lastIndex] - turnsNumberWasSaid[turnsNumberWasSaid.lastIndex - 1]
                }
            }

            lastNumber = currentNumber
            numberMap.addValue(currentNumber, index)
        }

        print("2020th number said is: $lastNumber")
        print("\nDONE :D")
    }

    fun solution2() {
        val startingNumbers = inputFile.readText().split(",").map { it.toInt() }
        val numberMap = mutableMapOf<Int, MutableList<Int>>()

        //configure starting conditions
        startingNumbers.forEachIndexed { index, number ->
            numberMap.addValue(number, index)
        }
        var lastNumber = startingNumbers.last()

        for (index in startingNumbers.size until 30000000) {
            val currentNumber = if (numberMap[lastNumber]!!.size == 1) {
                0
            } else {
                numberMap[lastNumber]!!.let { turnsNumberWasSaid ->
                    turnsNumberWasSaid[turnsNumberWasSaid.lastIndex] - turnsNumberWasSaid[turnsNumberWasSaid.lastIndex - 1]
                }
            }

            lastNumber = currentNumber
            numberMap.addValue(currentNumber, index)
        }

        print("2020th number said is: $lastNumber")
        print("\nDONE :D")
    }
}

fun MutableMap<Int, MutableList<Int>>.addValue(mapKey: Int, valueToAdd: Int) {
    this[mapKey]?.apply { add(valueToAdd) } ?: run { this[mapKey] = mutableListOf(valueToAdd) }
}