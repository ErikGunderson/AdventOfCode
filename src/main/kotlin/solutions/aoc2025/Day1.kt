package solutions.aoc2025

import solutions.utils.AoCProblem
import kotlin.math.abs

fun main() {
    Day1().runAllSolutions()
}

class Day1 : AoCProblem() {

    override val testInput = listOf(
        "L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L55",
        "L1",
        "L99",
        "R14",
        "L82"
    )

    fun solution1(input: List<String>) {
        val safeDial = SafeDial()

        input.forEach { dialInstruction ->
            safeDial.rotateDial(
                direction = dialInstruction.first(),
                amount = dialInstruction.substring(1).toInt()
            )
        }

        println("Door password is ${safeDial.hitZeroCount}")
    }

    fun solution2(input: List<String>) {
        val safeDial = SafeDial()

        input.forEach { dialInstruction ->
            safeDial.rotateDialPartTwo(
                direction = dialInstruction.first(),
                amount = dialInstruction.substring(1).toInt()
            )
        }

        println("Door password for method 0x434C49434B is ${safeDial.hitZeroCount}")
    }
}

private class SafeDial {
    private var currentPointer = 50

    var hitZeroCount = 0

    fun rotateDial(direction: Char, amount: Int) {
        currentPointer = if (direction == 'L') {
            (currentPointer - amount) % 100
        } else {
            (currentPointer + amount) % 100
        }

        if (currentPointer == 0) hitZeroCount++
    }

    fun rotateDialPartTwo(direction: Char, amount: Int) {
        hitZeroCount += amount / 100
        val rotateAmount = amount % 100

        currentPointer = if (direction == 'L') {
            if (currentPointer - rotateAmount < 0) {
                (100 - abs((currentPointer - rotateAmount))).also { newPointer ->
                    if (currentPointer < newPointer && currentPointer != 0) hitZeroCount++
                }
            } else {
                (currentPointer - rotateAmount)
            }
        } else {
            ((currentPointer + rotateAmount) % 100).also { newPointer ->
                if (currentPointer > newPointer && newPointer != 0) hitZeroCount++
            }
        }

        if (currentPointer == 0) hitZeroCount++
    }
}
