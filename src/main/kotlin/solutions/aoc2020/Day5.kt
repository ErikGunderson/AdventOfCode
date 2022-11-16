package solutions.aoc2020

import solutions.utils.AoCProblem
import kotlin.math.ceil
import kotlin.math.floor

class Day5 : AoCProblem() {
    fun solution1(input: List<String>) {
        //when taking the upper half (back) take the ceiling of the computed index
        //when taking the lower half (front) take the floor of the computer index

        //when taking the (right) take the ceiling
        //when taking the (left) take the floor

        var highestId = 0.0

        input.forEach { inputLine ->
            var front = 0.0
            var back = 127.0
            var left = 0.0
            var right = 7.0

            inputLine.forEach {
                when (it) {
                    'F' -> back = floor((front + back) / 2)
                    'B' -> front = ceil((front + back) / 2)
                    'R' -> left = ceil((left + right) / 2)
                    'L' -> right = floor((left + right) / 2)
                }
            }

            ((front * 8) + left).let { if (it > highestId) highestId = it }
        }

        print("Highest ID is $highestId")
        print("\nDONE :D")
    }

    fun solution2(input: List<String>) {
        //when taking the upper half (back) take the ceiling of the computed index
        //when taking the lower half (front) take the floor of the computer index

        //when taking the (right) take the ceiling
        //when taking the (left) take the floor

        val idList = mutableListOf<Int>()

        input.forEach { inputLine ->
            var front = 0.0
            var back = 127.0
            var left = 0.0
            var right = 7.0

            inputLine.forEach {
                when (it) {
                    'F' -> back = floor((front + back) / 2)
                    'B' -> front = ceil((front + back) / 2)
                    'R' -> left = ceil((left + right) / 2)
                    'L' -> right = floor((left + right) / 2)
                }
            }

            idList.add(((front * 8) + left).toInt())
        }

        idList.sorted().zipWithNext { current, next ->
            if (next - current == 2) {
                print("Your seat ID is ${(current + next) / 2}")
                print("\nDONE :D")
            }
        }
    }
}