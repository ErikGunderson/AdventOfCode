package solutions.aoc2021

import AoC2021Problem

class Day5 : AoC2021Problem() {
    fun solution1() {
        val startTimePerf = System.currentTimeMillis()
        val seafloorCoordinates = mutableListOf<SeafloorCoordinate>()

        inputFile.readLines().map { it.trim() }
            .forEach { inputLine ->
                val line = inputLine.split(" -> ")
                    .map { point -> point.split(",").let { Point(it[0].toInt(), it[1].toInt()) } }
                    .let { points -> getRange(points[0], points[1]) }

                if (line.isDiagonal()) return@forEach //For this one, do not consider diagonals

                line.forEach { seafloorCoordinates.getOrCreateCoordinate(it.xPos, it.yPos).ventCount++ }
            }

        print("Points with 2 or more overlaps ${seafloorCoordinates.count { it.ventCount >= 2 }}")
        print("\nDONE :D")
        print("\nExecution time: ${System.currentTimeMillis() - startTimePerf}")
    }

    fun solution2() {
        val startTimePerf = System.currentTimeMillis()
        val seafloorCoordinates = mutableListOf<SeafloorCoordinate>()

        inputFile.readLines().map { it.trim() }
            .forEach { inputLine ->
                val line = inputLine.split(" -> ")
                    .map { point -> point.split(",").let { Point(it[0].toInt(), it[1].toInt()) } }
                    .let { points -> getRange(points[0], points[1]) }

                line.forEach { seafloorCoordinates.getOrCreateCoordinate(it.xPos, it.yPos).ventCount++ }
            }

        print("Points with 2 or more overlaps ${seafloorCoordinates.count { it.ventCount >= 2 }}")
        print("\nDONE :D")
        print("\nExecution time: ${System.currentTimeMillis() - startTimePerf}")
    }

    private fun getRange(firstPoint: Point, secondPoint: Point) : PointRange {
        return if (firstPoint < secondPoint) firstPoint..secondPoint else secondPoint..firstPoint
    }
}

data class Point(
    val xPos: Int,
    val yPos: Int
): Comparable<Point> {
    override fun compareTo(other: Point): Int {
        return when {
            xPos == other.xPos && yPos == other.yPos -> 0
            xPos == other.xPos -> if (yPos < other.yPos) -1 else 1
            else -> if (xPos < other.xPos) -1 else 1
        }
    }

    fun makesDiagonalWith(other: Point) = xPos != other.xPos && yPos != other.yPos

    fun findSlope(other: Point) = (this.yPos - other.yPos) / (this.xPos - other.xPos)

    fun generatePointToward(other: Point): Point {
        return when {
            this.makesDiagonalWith(other) -> {
                val slope = findSlope(other)
                Point(xPos + 1, yPos + slope)
            }
            xPos == other.xPos -> Point(xPos, yPos + 1)
            else -> Point(xPos + 1, yPos)
        }
    }

    operator fun rangeTo(other: Point) = PointRange(this, other)
}

class PointRange(
    override val start: Point,
    override val endInclusive: Point
) : ClosedRange<Point>, Iterable<Point>{

    override fun iterator(): Iterator<Point> {
        return PointIterator(start, endInclusive)
    }

    fun isDiagonal() = start.makesDiagonalWith(endInclusive)
}

class PointIterator(start: Point, private val endInclusive: Point) : Iterator<Point> {

    var currentValue = start

    override fun hasNext(): Boolean {
        return currentValue <= endInclusive
    }

    override fun next(): Point {
        return currentValue.also { it.generatePointToward(endInclusive).apply { currentValue = this } }
    }
}

data class SeafloorCoordinate(
    val xPos: Int,
    val yPos: Int,
    var ventCount: Int = 0
)

fun MutableList<SeafloorCoordinate>.getOrCreateCoordinate(xPos: Int, yPos: Int): SeafloorCoordinate {
    return this.find { it.xPos == xPos && it.yPos == yPos}
        ?: SeafloorCoordinate(xPos, yPos).apply { this@getOrCreateCoordinate.add(this) }
}