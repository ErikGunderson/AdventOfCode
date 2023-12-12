package solutions.aoc2023

import solutions.utils.AoCProblem
import kotlin.math.max
import kotlin.math.min

fun main() {
    Day11().runSolution(2)
}

class Day11: AoCProblem() {

    override val testInput: List<String>
        get() = listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#....."
        )

    fun solution1(input: List<String>) = input.let { galaxyMap ->
        val emptyColumns = mutableListOf<Int>()
        val emptyRows = mutableListOf<Int>()

        val galaxies = mutableListOf<GalaxyLocation>()

        //Find all empty rows and columns, and all galaxies
        (0..galaxyMap.lastIndex).forEach { columnIndex ->
            if (galaxyMap.map { it[columnIndex] }.all { it == '.' }) emptyColumns.add(columnIndex)
        }

        galaxyMap.forEachIndexed { rowIndex, row ->
            row.mapIndexedNotNull { columnIndex, cell -> if (cell == '#') columnIndex to rowIndex else null }
                .let { galaxyLocations ->
                    if (galaxyLocations.isEmpty()) emptyRows.add(rowIndex) else galaxies.addAll(galaxyLocations)
                }
        }

        //Pair up all the galaxies
        val pairs = galaxies.mapIndexed { index, galaxy ->
            ((index + 1)..galaxies.lastIndex).map { pairingIndex -> galaxy to galaxies[pairingIndex] }
        }.flatten()

        //Compute distances (abs(diff in x) + abs(diff in y)) + 1 extra for each empty column or row the range of X and Y values passes over
        val sumOfPairDistances = pairs.sumOf { (firstGalaxy, secondGalaxy) ->
            val xRange = min(firstGalaxy.x, secondGalaxy.x)..max(firstGalaxy.x, secondGalaxy.x)
            val yRange = min(firstGalaxy.y, secondGalaxy.y)..max(firstGalaxy.y, secondGalaxy.y)

            val crossedEmptyColumns = emptyColumns.count { xRange.contains(it) }
            val crossedEmptyRows = emptyRows.count { yRange.contains(it) }

            xRange.distanceCovered() + yRange.distanceCovered() + crossedEmptyColumns + crossedEmptyRows
        }

        println("Sum of distances between all pairs: $sumOfPairDistances")
    }

    fun solution2(input: List<String>) = input.let { galaxyMap ->
        val emptyColumns = mutableListOf<Int>()
        val emptyRows = mutableListOf<Int>()

        val galaxies = mutableListOf<GalaxyLocation>()

        //Find all empty rows and columns, and all galaxies
        (0..galaxyMap.lastIndex).forEach { columnIndex ->
            if (galaxyMap.map { it[columnIndex] }.all { it == '.' }) emptyColumns.add(columnIndex)
        }

        galaxyMap.forEachIndexed { rowIndex, row ->
            row.mapIndexedNotNull { columnIndex, cell -> if (cell == '#') columnIndex to rowIndex else null }
                .let { galaxyLocations ->
                    if (galaxyLocations.isEmpty()) emptyRows.add(rowIndex) else galaxies.addAll(galaxyLocations)
                }
        }

        //Pair up all the galaxies
        val pairs = galaxies.mapIndexed { index, galaxy ->
            ((index + 1)..galaxies.lastIndex).map { pairingIndex -> galaxy to galaxies[pairingIndex] }
        }.flatten()

        //Compute distances (abs(diff in x) + abs(diff in y)) + 1 extra for each empty column or row the range of X and Y values passes over
        val sumOfPairDistances: Long = pairs.sumOf { (firstGalaxy, secondGalaxy) ->
            val xRange = min(firstGalaxy.x, secondGalaxy.x)..max(firstGalaxy.x, secondGalaxy.x)
            val yRange = min(firstGalaxy.y, secondGalaxy.y)..max(firstGalaxy.y, secondGalaxy.y)

            val crossedEmptyColumns = emptyColumns.count { xRange.contains(it) }.toLong()
            val crossedEmptyRows = emptyRows.count { yRange.contains(it) }.toLong()

            xRange.distanceCovered() +
                    yRange.distanceCovered() +
                    ((crossedEmptyColumns * 1_000_000) - crossedEmptyColumns) +
                    ((crossedEmptyRows * 1_000_000) - crossedEmptyRows)
        }

        println("Sum of distances between all pairs: $sumOfPairDistances")
    }
}

private data class GalaxyLocation(
    val x: Int,
    val y: Int
)

private infix fun Int.to(that: Int): GalaxyLocation = GalaxyLocation(this, that)

private fun IntRange.distanceCovered(): Long = (this.last - this.first).toLong()