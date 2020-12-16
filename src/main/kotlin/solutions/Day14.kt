package solutions

import FileReader
import java.util.*
import kotlin.math.exp
import kotlin.math.pow

class Day14 : FileReader() {
    fun solution1() {
        val registerMap = mutableMapOf<Int, Long>()
        var currentBitmask = listOf<BitmaskValue>()

        inputFile.readLines().forEach {
            val splitInput = it.split(" = ")

            if (splitInput[0] == "mask") {
                currentBitmask = splitInput[1].reversed()
                    .mapIndexed { index, value -> index to value }
                    .mapNotNull { if (it.second == 'X') null else it }
                    .map { BitmaskValue(it.first, it.second) }
            } else {
                //process register assign
                val register = splitInput[0].substring(4, splitInput[0].lastIndex).toInt()
                val bits = BitSet.valueOf(longArrayOf(splitInput[1].toLong()))

                currentBitmask.forEach {
                    bits.set(it.index, it.value == '1')
                }

                registerMap[register] = bits.toLongArray()[0]
            }
        }

        print("Register values sum is: ${registerMap.values.sum()}")
        print("\nDONE :D")
    }

//    fun solution2() {
//        val registerMap = mutableMapOf<Int, Long>()
//        var currentBitmask = listOf<BitmaskValue>()
//
//        inputFile.readLines().forEach {
//            val splitInput = it.split(" = ")
//
//            if (splitInput[0] == "mask") {
//                currentBitmask = splitInput[1].reversed().mapIndexed { index, value -> BitmaskValue(index, value) }
//            } else {
//                //process register assign
//                val preProcessingRegister = splitInput[0].substring(4, splitInput[0].lastIndex).toLong()
//                val registersToWrite = mutableListOf<Long>()
//                val value = splitInput[1].toLong()
//                val baseRegister = BitSet.valueOf(longArrayOf(preProcessingRegister))
//                    .applyMask(currentBitmask.map { if (it.value == 'X') '0' else it.value })
//                    .toLongArray()[0]
//
//                val floatingBinaryValues = currentBitmask.mapIndexed { index, bitmaskValue -> index to bitmaskValue.value }
//                    .filter { it.second == 'X' }
//                    .map { 2.toFloat().pow(it.first)}
//
//                registersToWrite.add(baseRegister)
//
//                floatingBinaryValues.forEachIndexed { index, fl ->  } {
//
//                }
//
//
//                registersToWrite.forEach {
//                    registerMap[it] = value
//                }
//            }
//        }
//
//        print("Register values sum is: ${registerMap.values.sum()}")
//        print("\nDONE :D")
//    }
}

data class BitmaskValue(
    val index: Int,
    val value: Char
)

fun BitSet.applyMask(mask: List<Char>): BitSet {
    mask.forEachIndexed { index, maskValue ->
        set(index, maskValue == '1')
    }

    return this
}