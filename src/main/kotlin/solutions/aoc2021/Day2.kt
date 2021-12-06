package solutions.aoc2021

import AoC2021Problem

class Day2: AoC2021Problem() {
    override fun solution1() {
        val coords = SubCoordinate()
        inputFile.readLines().map { it.trim() }.forEach { input ->
            Regex("\\s+(?=\\d)").splitToSequence(input, 2).iterator().let {
                when (it.next().trim()) {
                    "forward" -> coords.horizontalPosition += it.next().toInt()
                    "down" -> coords.depth += it.next().toInt()
                    "up" -> coords.depth -= it.next().toInt()
                }
            }
        }

        print("Depth is ${coords.depth}, horizontal position is ${coords.horizontalPosition}, product is ${coords.depth * coords.horizontalPosition}")
    }

    override fun solution2() {
        val coords = SubCoordinate()
        inputFile.readLines().map { it.trim() }.forEach { input ->
            Regex("\\s+(?=\\d)").splitToSequence(input, 2).iterator().let {
                when (it.next().trim()) {
                    "forward" -> {
                        val movementAmount = it.next().toInt()
                        coords.horizontalPosition += movementAmount
                        coords.depth += coords.aim * movementAmount
                    }
                    "down" -> coords.aim += it.next().toInt()
                    "up" -> coords.aim -= it.next().toInt()
                }
            }
        }

        print("Depth is ${coords.depth}, horizontal position is ${coords.horizontalPosition}, product is ${coords.depth * coords.horizontalPosition}")
    }
}

class SubCoordinate(
    var depth: Int = 0,
    var horizontalPosition: Int = 0,
    var aim: Int = 0
)