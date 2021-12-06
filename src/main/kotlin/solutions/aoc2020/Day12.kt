package solutions.aoc2020

import AoC2020Problem
import solutions.aoc2020.CardinalDirection.*
import kotlin.math.abs

class Day12 : AoC2020Problem() {
    override fun solution1(input: List<String>) {
        val cardinalMovementMap = mutableMapOf(
            North to 0,
            South to 0,
            East to 0,
            West to 0
        )
        var currentFacing = East

        inputFile.readLines().forEach {
            val direction = it.first()
            var units = it.substring(1..it.lastIndex).toInt()

            when (direction) {
                'N' -> cardinalMovementMap[North] = cardinalMovementMap[North]!!.plus(units)
                'S' -> cardinalMovementMap[South] = cardinalMovementMap[South]!!.plus(units)
                'E' -> cardinalMovementMap[East] = cardinalMovementMap[East]!!.plus(units)
                'W' -> cardinalMovementMap[West] = cardinalMovementMap[West]!!.plus(units)
                'L' -> {
                    while (units > 0) {
                        units -= 90
                        currentFacing = currentFacing.turnLeft()
                    }
                }
                'R' -> {
                    while (units > 0) {
                        units -= 90
                        currentFacing = currentFacing.turnRight()
                    }
                }
                'F' -> cardinalMovementMap[currentFacing] = cardinalMovementMap[currentFacing]!!.plus(units)
            }
        }

        val manhattanDistance = abs(cardinalMovementMap[North]!!.minus(cardinalMovementMap[South]!!)) +
                abs(cardinalMovementMap[East]!!.minus(cardinalMovementMap[West]!!))

        print("Ship's manhattan distance is: $manhattanDistance")
        print("\nDONE :D")
    }

    override fun solution2(input: List<String>) {
        val shipCardinalMovementMap = mutableMapOf(
            North to 0,
            South to 0,
            East to 0,
            West to 0
        )
        val waypointOffsetMap = mutableMapOf(
            North to 1,
            South to 0,
            East to 10,
            West to 0
        )

        inputFile.readLines().forEach {
            val direction = it.first()
            var units = it.substring(1..it.lastIndex).toInt()

            when (direction) {
                'N' -> waypointOffsetMap[North] = waypointOffsetMap[North]!!.plus(units)
                'S' -> waypointOffsetMap[South] = waypointOffsetMap[South]!!.plus(units)
                'E' -> waypointOffsetMap[East] = waypointOffsetMap[East]!!.plus(units)
                'W' -> waypointOffsetMap[West] = waypointOffsetMap[West]!!.plus(units)
                'L' -> {
                    while (units > 0) {
                        val newWaypointNorth = waypointOffsetMap[East]!!
                        val newWaypointEast = waypointOffsetMap[South]!!
                        val newWaypointSouth = waypointOffsetMap[West]!!
                        val newWaypointWest = waypointOffsetMap[North]!!

                        waypointOffsetMap[North] = newWaypointNorth
                        waypointOffsetMap[East] = newWaypointEast
                        waypointOffsetMap[South] = newWaypointSouth
                        waypointOffsetMap[West] = newWaypointWest

                        units -= 90
                    }
                }
                'R' -> {
                    while (units > 0) {
                        val newWaypointNorth = waypointOffsetMap[West]!!
                        val newWaypointEast = waypointOffsetMap[North]!!
                        val newWaypointSouth = waypointOffsetMap[East]!!
                        val newWaypointWest = waypointOffsetMap[South]!!

                        waypointOffsetMap[North] = newWaypointNorth
                        waypointOffsetMap[East] = newWaypointEast
                        waypointOffsetMap[South] = newWaypointSouth
                        waypointOffsetMap[West] = newWaypointWest

                        units -= 90
                    }
                }
                'F' -> {
                    for (i in 0 until units) {
                        shipCardinalMovementMap[North] = shipCardinalMovementMap[North]!!.plus(waypointOffsetMap[North]!!)
                        shipCardinalMovementMap[South] = shipCardinalMovementMap[South]!!.plus(waypointOffsetMap[South]!!)
                        shipCardinalMovementMap[East] = shipCardinalMovementMap[East]!!.plus(waypointOffsetMap[East]!!)
                        shipCardinalMovementMap[West] = shipCardinalMovementMap[West]!!.plus(waypointOffsetMap[West]!!)
                    }
                }
            }
        }

        val manhattanDistance = abs(shipCardinalMovementMap[North]!!.minus(shipCardinalMovementMap[South]!!)) +
                abs(shipCardinalMovementMap[East]!!.minus(shipCardinalMovementMap[West]!!))

        print("Ship's manhattan distance is: $manhattanDistance")
        print("\nDONE :D")
    }
}

enum class CardinalDirection {
    North,
    East,
    South,
    West
}

fun CardinalDirection.turnLeft() : CardinalDirection {
    val newOrdinal = (this.ordinal - 1).let { if (it < 0) CardinalDirection.values().lastIndex else it }
    return CardinalDirection.values()[newOrdinal]
}

fun CardinalDirection.turnRight() : CardinalDirection {
    val newOrdinal = (this.ordinal + 1).let { if (it > CardinalDirection.values().lastIndex) 0 else it }
    return CardinalDirection.values()[newOrdinal]
}