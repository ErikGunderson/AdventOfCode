package solutions.aoc2020

import AoC2020Problem
import java.util.*

class Day14 : AoC2020Problem() {
    override fun solution1() {
        val registerMap = mutableMapOf<Int, Long>()
        var currentBitmask = listOf<BitmaskValue>()

        inputFile.readLines().forEach {
            val splitInput = it.split(" = ")

            if (splitInput[0] == "mask") {
                currentBitmask = splitInput[1].reversed()
                    .mapIndexed { index, value -> index to value }
                    .mapNotNull { if (it.second == 'X') null else it }
                    .map { BitmaskValue(it.first, it.second == '1') }
            } else {
                //process register assign
                val register = splitInput[0].substring(4, splitInput[0].lastIndex).toInt()
                val bits = BitSet.valueOf(longArrayOf(splitInput[1].toLong()))

                currentBitmask.forEach {
                    bits.set(it.index, it.value)
                }

                registerMap[register] = bits.toLongArray()[0]
            }
        }

        print("Register values sum is: ${registerMap.values.sum()}")
        print("\nDONE :D")
    }

    override fun solution2() {
        val registerMap = mutableMapOf<Long, Long>()
        var currentBitmaskList = listOf(listOf<BitmaskValue>())

        inputFile.readLines().forEach {
            val splitInput = it.split(" = ")

            if (splitInput[0] == "mask") {
                currentBitmaskList = splitInput[1]
                    .reversed()
                    .mapIndexed { index, value -> Pair(index, value) }
                    .toBitmaskList()
            } else {
                //process register assign
                val register = splitInput[0].substring(4, splitInput[0].lastIndex).toLong()
                val value = splitInput[1].toLong()

                val registersToWrite = currentBitmaskList.map { register.applyMask(it) }

                registersToWrite.forEach {
                    registerMap[it] = value
                }
            }
        }

        print("Register values sum is: ${registerMap.values.sum()}")
        print("\nDONE :D")
    }
}

data class BitmaskValue(
    val index: Int,
    var value: Boolean,
    var isFloating: Boolean = false
)

fun Long.applyMask(mask: List<BitmaskValue>): Long {
    val tmpBitset = BitSet.valueOf(longArrayOf(this))

    mask.forEach {
        if (it.isFloating || it.value) tmpBitset.set(it.index, it.value)
    }

    return tmpBitset.toLongArray()[0]
}

fun generateAllBitmaskValues(
    permutationLength: Int,
    arr: MutableList<BitmaskValue>,
    index: Int,
    outputList: MutableList<List<BitmaskValue>>
) {
    if (index == permutationLength) {
        outputList.add(arr.map { it.copy() })
        return
    }

    // First assign "0" at ith position
    // and try for all other permutations
    // for remaining positions
    arr[index].value = false
    generateAllBitmaskValues(permutationLength, arr, index + 1, outputList)

    // And then assign "1" at ith position
    // and try for all other permutations
    // for remaining positions
    arr[index].value = true
    generateAllBitmaskValues(permutationLength, arr, index + 1, outputList)
}

fun List<Pair<Int, Char>>.toBitmaskList(): List<List<BitmaskValue>> {
    val outputList = mutableListOf<List<BitmaskValue>>()
    val tmpList = this.filter { it.second == 'X' }.map { BitmaskValue(it.first, false, true) }.toMutableList()

    generateAllBitmaskValues(tmpList.size, tmpList, 0, outputList)

    return outputList.map { outputMaskSequence ->
        this.map { BitmaskValue(it.first, it.second == '1') }.toMutableList().apply {
            outputMaskSequence.forEach { bitmaskValue ->
                this[bitmaskValue.index] = bitmaskValue
            }
        }
    }
}