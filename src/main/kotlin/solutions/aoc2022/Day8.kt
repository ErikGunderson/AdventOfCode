package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day8().runAllSolutions()
}

class Day8: AoCProblem() {
    fun solution1(input: List<String>) {
        input.map { it.map { it.digitToInt() } }
    }

    fun solution2(input: List<String>) {

    }
}
//
//30373
//25512
//65332
//33549
//35390
//
//32633
//05535
//35353
//71349
//32290
//
//        [0, 3]
//        [3, 0]
//
//        [3, 4]
//        [4, 3]