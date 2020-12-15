package solutions

import FileReader
import java.lang.Math.ceil
import kotlin.math.round

class Day13 : FileReader() {

    var currentLcmMultiplier = 1L

    fun solution1() {
        inputFile.readLines().let {
            val targetTime = it[0].toInt()
            val busIds = it[1].split(",").mapNotNull {
                if (it == "x") null
                else it.toInt()
            }

            var bestBus = 0 to Int.MAX_VALUE
            busIds.forEach { busId ->
                var currentArrival = busId
                while (currentArrival < targetTime) {
                    currentArrival += busId
                }
                (currentArrival - targetTime).let { difference ->
                    if (difference < bestBus.second) bestBus = busId to difference
                }
            }

            print("earliest bus ID: ${bestBus.first} minutes waiting is: ${bestBus.second} multipled is: " +
                    "${bestBus.first * bestBus.second}")
            print("\nDONE :D")
        }
    }

    fun solution2() {
        val buses = inputFile.readLines().let {
            it[1].split(",").mapIndexed { index, busId ->
                busId to index
            }
        }.filter { it.first != "x" }.map { Bus(it.first.toInt(), it.second) }.toMutableList()

        val referenceBus = buses.find { it.requiredOffset == 0 }!!
        val referencePairBus = buses.find { it.requiredOffset == referenceBus.busId }!!

        buses.removeAll(listOf(referenceBus, referencePairBus))

        val lowestCommonMultipleRefAndPair = referenceBus.busId.findLowestCommonMultiple(referencePairBus.busId)

        var refNumber = findNextRefNumber(lowestCommonMultipleRefAndPair, referenceBus.busId)

        currentLcmMultiplier = ceil(100000000000000.toDouble()/ lowestCommonMultipleRefAndPair).toLong()

        var busIndex = 0
        while (busIndex <= buses.lastIndex) {
            val currentBus = buses[busIndex]
            busIndex += 1

            var currentMultiplier = ceil(100000000000000.toDouble()/ currentBus.busId).toLong()
            var currentValue = (currentBus.busId * currentMultiplier) - currentBus.requiredOffset

            while (refNumber != currentValue) {
                if (refNumber < currentValue) {
                    currentLcmMultiplier += 1
                    refNumber = findNextRefNumber(lowestCommonMultipleRefAndPair, referenceBus.busId)
                    busIndex = 0
                } else {
                    currentMultiplier += 1
                    currentValue = (currentBus.busId * currentMultiplier) - currentBus.requiredOffset
                }
            }
        }

        print("earliest valid timestamp: $refNumber")
        print("\nDONE :D")
    }

    private fun findNextRefNumber(lcm: Int, offset: Int) : Long {
        var tmp = ((lcm * currentLcmMultiplier) - offset)
        while (tmp % offset != 0L) {
            currentLcmMultiplier += 1
            tmp = ((lcm * currentLcmMultiplier) - offset)
        }
        return tmp
    }
}

data class Bus(
    val busId: Int,
    val requiredOffset: Int
)

fun Int.findLowestCommonMultiple(other: Int) : Int {
    var currentMultiplier = 1

    while ((other * currentMultiplier) % this != 0) {
        currentMultiplier += 1
    }

    return other * currentMultiplier
}