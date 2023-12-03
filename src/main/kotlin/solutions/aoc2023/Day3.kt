package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day3().runAllSolutions()
}

class Day3 : AoCProblem() {
    override val testInput: List<String>
        get() = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598.."
        )

    fun solution1(input: List<String>) {
        val partNumbersNearSymbol = mutableSetOf<Number>()

        identifySymbolsAndPartNumbers(input, "[^\\d.]") { adjacentPartNumbers ->
            partNumbersNearSymbol.addAll(adjacentPartNumbers)
        }

        print("The sum of part numbers near a symbol is ${partNumbersNearSymbol.sumOf { it.value }}\n")
    }

    fun solution2(input: List<String>) {
        var gearRatioSum = 0L

        identifySymbolsAndPartNumbers(input, "\\*") { adjacentPartNumbers ->
            if (adjacentPartNumbers.size == 2) gearRatioSum += adjacentPartNumbers[0].value * adjacentPartNumbers[1].value
        }

        print("The gear ratio sum is $gearRatioSum\n")
    }
}

private fun identifySymbolsAndPartNumbers(
    input: List<String>,
    symbolsToIdentifyRegex: String,
    onAdjacentPartNumbers: (List<Number>) -> Unit
) {
    val partNumbers = mutableMapOf<Int /* Y INDEX */, MutableList<Number>>()
    val gearSymbols = mutableListOf<Symbol>()

    input.forEachIndexed { yIndex, rowString ->
        Regex("(\\d+)|($symbolsToIdentifyRegex+?)").findAll(rowString).forEach { match ->
            match.value.toIntOrNull()?.let { matchInt ->
                partNumbers.merge(yIndex, mutableListOf(Number(matchInt, match.range to yIndex))) { oldValue, newValue ->
                    oldValue.apply { addAll(newValue) }
                }
            } ?: run { gearSymbols += Symbol(match.value, match.range.first to yIndex) }
        }
    }

    //To be adjacent, the Y coordinate of a symbol must be +/- 1 of the Y of a number, and the X coordinates must either be touching or overlapping
    gearSymbols.forEach { symbol ->
        mutableListOf<Number>()
            .apply {
                partNumbers[symbol.coordinate.y - 1]?.let { addAll(it) }
                partNumbers[symbol.coordinate.y]?.let { addAll(it) }
                partNumbers[symbol.coordinate.y + 1]?.let { addAll(it) }
            }
            .filter { it.isAdjacentTo(symbol) }
            .let { adjacentPartNumbers -> onAdjacentPartNumbers(adjacentPartNumbers) }
    }
}

private data class Symbol(
    val char: String,
    val coordinate: Coordinate
)

private data class Number(
    val value: Int,
    val coordinates: CoordinateRange
) {
    fun isAdjacentTo(symbol: Symbol): Boolean = coordinates.xRange.let { (it.first - 1)..(it.last + 1) }.contains(symbol.coordinate.x)
}

private data class Coordinate(val x: Int, val y: Int)
private data class CoordinateRange(val xRange: IntRange, val y: Int)

private infix fun Int.to(that: Int): Coordinate = Coordinate(this, that)
private infix fun IntRange.to(that: Int): CoordinateRange = CoordinateRange(this, that)
