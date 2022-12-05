package solutions.aoc2022

import solutions.utils.AoCProblem
import java.util.*

fun main() {
    Day5().runAllSolutions()
}

class Day5: AoCProblem() {
    fun solution1(input: List<String>) {
        val dataSetSeparatorIndex = input.indexOfFirst { it.isBlank() }
        val stacks = input[dataSetSeparatorIndex - 1].split("   ").maxOf { it.toInt() }.let { Array(it) { Stack<Char>() } }

        untrimmedInput.subList(0, dataSetSeparatorIndex - 1).asReversed().forEach { row ->
            val crateRow = row.replace(oldValue = "    ", newValue = "*").filterNot { it == '[' || it == ']' || it == ' '}
            crateRow.forEachIndexed { index, char -> if (char != '*') stacks[index].push(char) }
        }

        input.subList(dataSetSeparatorIndex + 1, input.size).forEach { instruction ->
            Regex("\\d+").findAll(instruction).toList()
                .let { CrateMoveInstruction(it[0].value.toInt(), it[1].value.toInt(), it[2].value.toInt()) }
                .let { for (crate in 1..it.count) stacks[it.targetStack - 1].push(stacks[it.destinationStack - 1].pop()) }
        }

        print("Top of stacks: ${String(stacks.map { it.peek() }.toCharArray())}")
    }

    fun solution2(input: List<String>) {
        val dataSetSeparatorIndex = input.indexOfFirst { it.isBlank() }
        val stacks = input[dataSetSeparatorIndex - 1].split("   ").maxOf { it.toInt() }.let { Array(it) { Stack<Char>() } }

        untrimmedInput.subList(0, dataSetSeparatorIndex - 1).asReversed().forEach { row ->
            val crateRow = row.replace(oldValue = "    ", newValue = "*").filterNot { it == '[' || it == ']' || it == ' '}
            crateRow.forEachIndexed { index, char -> if (char != '*') stacks[index].push(char) }
        }

        input.subList(dataSetSeparatorIndex + 1, input.size).forEach { instruction ->
            Regex("\\d+").findAll(instruction).toList()
                .let { CrateMoveInstruction(it[0].value.toInt(), it[1].value.toInt(), it[2].value.toInt()) }
                .let {
                    stacks[it.targetStack - 1].addAll(stacks[it.destinationStack - 1].takeLast(it.count))
                    for (crate in 1..it.count) stacks[it.destinationStack - 1].removeLast()
                }
        }

        print("Top of stacks: ${String(stacks.map { it.peek() }.toCharArray())}")
    }

    private data class CrateMoveInstruction(
        val count: Int,
        val destinationStack: Int,
        val targetStack: Int
    )
}