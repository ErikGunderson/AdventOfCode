package solutions.aoc2020

import AoC2020Problem

class Day23 : AoC2020Problem() {
    override fun solution1() {
        val cups = mutableListOf<Cup>()
        var currentCupIndex = 0

        inputFile.readText().forEach {
            cups.add(Cup(it.toString().toInt()))
        }

        for (move in 0 until 100) {
            currentCupIndex = moveCups(cups, currentCupIndex, cups.size)
            print("Completed move $move\n")
        }

        val cupOrderWrapped = cups.indexOfFirst { it.label == 1 }
            .let { cups.getSublistWrapped((it + 1) % cups.size, it) }
            .map { it.label }
            .joinToString("")

        print("Cup order after 100 moves: $cupOrderWrapped")
        print("\nDONE :D")
    }

    override fun solution2() {
        val cups = mutableListOf<Cup>()
        var currentCupIndex = 0

        inputFile.readText().forEach {
            cups.add(Cup(it.toString().toInt()))
        }

        for (label in 10..1000000) {
            cups.add(Cup(label))
        }

        for (move in 0 until 10000000) {
            currentCupIndex = moveCups(cups, currentCupIndex, cups.size)
//            print("Completed move $move\n")
        }

        val output = cups.indexOfFirst { it.label == 1 }
            .let { cups[(it + 1) % cups.size].label * cups[(it + 2) % cups.size].label }

        print("Output after 10000000 moves: $output")
        print("\nDONE :D")
    }

    private fun moveCups(cups: MutableList<Cup>, currentCupIndex: Int, maxCupCount: Int): Int {
        val pickedUpCups =
            cups.getSublistWrapped((currentCupIndex + 1) % cups.size, (currentCupIndex + 4) % cups.size).toList()
        val currentCupLabel = cups[currentCupIndex].label

        cups.removeAll(pickedUpCups)

        val destinationCupIndex = findDestinationCupIndex(currentCupLabel, cups, pickedUpCups, maxCupCount)

        cups.addAll(destinationCupIndex + 1, pickedUpCups)

        return if (destinationCupIndex < currentCupIndex) {
            if (currentCupIndex + 4 > cups.size) 0 else (currentCupIndex + 4) % cups.size
        } else {
            (currentCupIndex + 1) % cups.size
        }
    }

    private fun findDestinationCupIndex(
        currentLabel: Int,
        cups: List<Cup>,
        pickedUpCups: List<Cup>,
        maxCupCount: Int
    ): Int {
        val minMaxLabels = findMinMaxLabels(pickedUpCups.map { it.label }, maxCupCount)
        var destinationCupIndex: Int = -1
        var tmpLabel = currentLabel

        while (destinationCupIndex == -1) {
            tmpLabel -= 1
            if (tmpLabel < minMaxLabels.first) tmpLabel = minMaxLabels.second

            destinationCupIndex = cups.indexOfFirst { it.label == tmpLabel }
        }

        return destinationCupIndex
    }

    private fun findMinMaxLabels(pickedUpCupLabels: List<Int>, maxCupCount: Int): Pair</* Min */Int, /*Max */Int> {
        var max = maxCupCount
        var min = 1

        for (check in 0 until 3) {
            if (max in pickedUpCupLabels) max -= 1
            if (min in pickedUpCupLabels) min += 1
        }

        return min to max
    }
}

data class Cup(
    val label: Int
)

fun List<Cup>.getSublistWrapped(firstIndex: Int, secondIndex: Int): List<Cup> {
    return if (secondIndex < firstIndex) {
        //Needs to wrap
        mutableListOf<Cup>().apply tmpList@{
            addAll(this@getSublistWrapped.slice(firstIndex..this@getSublistWrapped.lastIndex).toList())
            addAll(this@getSublistWrapped.slice(0 until secondIndex).toList())
        }
    } else {
        //no wrap needed
        this.slice(firstIndex until secondIndex).toList()
    }
}