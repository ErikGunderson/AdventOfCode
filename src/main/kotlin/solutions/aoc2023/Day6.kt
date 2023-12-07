package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day6().runSolution(2)
}

class Day6: AoCProblem() {
    override val testInput: List<String>
        get() = listOf(
            "Time:      7  15   30",
            "Distance:  9  40  200"
        )

    fun solution1(input: List<String>) {
        val times = Regex("(\\d+)+").findAll(input[0]).map { it.value.toInt() }
        val recordDistances = Regex("(\\d+)+").findAll(input[1]).map { it.value.toInt() }.toList()

        val waysToBeatRecord = times.mapIndexed { index, totalRaceTime ->
            val recordDistance = recordDistances[index]

            var firstRecordBeatingHoldTime = -1
            var lastRecordBeatingHoldTime = -1

            (1..totalRaceTime).let { holdButtonRange ->
                run forward@ {
                    holdButtonRange.forEach { buttonHoldTime ->
                        if (buttonHoldTime * (totalRaceTime - buttonHoldTime) > recordDistance) {
                            firstRecordBeatingHoldTime = buttonHoldTime
                            return@forward
                        }
                    }
                }

                //reverse
                run backward@{
                    holdButtonRange.reversed().forEach { buttonHoldTime ->
                        if (buttonHoldTime * (totalRaceTime - buttonHoldTime) > recordDistance) {
                            lastRecordBeatingHoldTime = buttonHoldTime
                            return@backward
                        }
                    }
                }
            }

            println("first hold time $firstRecordBeatingHoldTime last hold time $lastRecordBeatingHoldTime")

            lastRecordBeatingHoldTime - firstRecordBeatingHoldTime + 1
        }

        println("Multiplied ways to win all races: ${waysToBeatRecord.reduce { acc, waysToWin -> acc * waysToWin }}")
    }

    fun solution2(input: List<String>) {
        val totalRaceTime = Regex("(\\d+)+").findAll(input[0]).joinToString("") { it.value }.toLong()
        val recordDistance = Regex("(\\d+)+").findAll(input[1]).joinToString("") { it.value }.toLong()

        var firstRecordBeatingHoldTime = -1L
        var lastRecordBeatingHoldTime = -1L

        (1..totalRaceTime).let { holdButtonRange ->
            run forward@ {
                holdButtonRange.forEach { buttonHoldTime ->
                    if (buttonHoldTime * (totalRaceTime - buttonHoldTime) > recordDistance) {
                        firstRecordBeatingHoldTime = buttonHoldTime
                        return@forward
                    }
                }
            }

            //reverse
            run backward@{
                holdButtonRange.reversed().forEach { buttonHoldTime ->
                    if (buttonHoldTime * (totalRaceTime - buttonHoldTime) > recordDistance) {
                        lastRecordBeatingHoldTime = buttonHoldTime
                        return@backward
                    }
                }
            }
        }

        println("first hold time $firstRecordBeatingHoldTime last hold time $lastRecordBeatingHoldTime")

        println("total ways to beat the record for this race: ${lastRecordBeatingHoldTime - firstRecordBeatingHoldTime + 1}")
    }
}