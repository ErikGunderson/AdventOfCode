package solutions.aoc2021

import AoC2021Problem
import kotlin.math.*

class Day9 : AoC2021Problem() {

    override fun solution1(input: List<String>) {
        val riskLevelSum = input.map { it.map { digitChar -> digitChar.digitToInt() } }.let { inputRows ->
            inputRows.mapIndexed { rowIndex, rowHeightMap ->
                val heightMapAbove = inputRows.getOrNull(rowIndex - 1)
                val heightMapBelow = inputRows.getOrNull(rowIndex + 1)

                rowHeightMap.mapIndexed { columnIndex, heightValue ->
                    val comparisonValues = listOfNotNull(
                        rowHeightMap.getOrNull(columnIndex - 1),
                        rowHeightMap.getOrNull(columnIndex + 1),
                        heightMapAbove?.getOrNull(columnIndex),
                        heightMapBelow?.getOrNull(columnIndex)
                    )

                    if (heightValue < comparisonValues.minOf { it }) (heightValue + 1) else 0
                }.sum()
            }
        }.sum()

        print("Total risk level of height map: $riskLevelSum")
    }

    override fun solution2(input: List<String>) {
//        //TODO form columns, scan left to right
//        val basinSizes = input.map { it.map { digitChar -> digitChar.digitToInt() } }.mapIndexed { index, row ->
//
//        }
//
//
//
//            .let { inputRows ->
//            inputRows.mapIndexed { rowIndex, rowHeightMap ->
//
//
//                val heightMapAbove = inputRows.getOrNull(rowIndex - 1)
//                val heightMapBelow = inputRows.getOrNull(rowIndex + 1)
//
//                rowHeightMap.mapIndexed { columnIndex, heightValue ->
//                    val comparisonValues = listOfNotNull(
//                        rowHeightMap.getOrNull(columnIndex - 1),
//                        rowHeightMap.getOrNull(columnIndex + 1),
//                        heightMapAbove?.getOrNull(columnIndex),
//                        heightMapBelow?.getOrNull(columnIndex)
//                    )
//
//                    if (heightValue < comparisonValues.minOf { it }) (heightValue + 1) else 0
//                }.sum()
//            }
//        }
//
//
//        print("Total risk level of height map: $riskLevelSum")
    }
}