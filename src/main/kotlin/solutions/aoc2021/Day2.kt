package solutions.aoc2021

import solutions.utils.AoCProblem

class Day2: AoCProblem() {
    fun solution1(input: List<String>) {
        val coords = SubCoordinate()
        input.forEach { singleInput ->
            Regex("\\s+(?=\\d)").splitToSequence(singleInput, 2).iterator().let {
                when (it.next().trim()) {
                    "forward" -> coords.horizontalPosition += it.next().toInt()
                    "down" -> coords.depth += it.next().toInt()
                    "up" -> coords.depth -= it.next().toInt()
                }
            }
        }

        print("Depth is ${coords.depth}, horizontal position is ${coords.horizontalPosition}, product is ${coords.depth * coords.horizontalPosition}")
    }

    fun solution2(input: List<String>) {
        val coords = SubCoordinate()
        input.forEach { singleInput ->
            Regex("\\s+(?=\\d)").splitToSequence(singleInput, 2).iterator().let {
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