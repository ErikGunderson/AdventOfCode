package solutions.aoc2020

import AoC2020Problem

class Day8 : AoC2020Problem() {
    override fun solution1(input: List<String>) {
        var accumulator = 0
        val instructions = mutableListOf<Instruction>()
        input.forEachIndexed { index, singleInput ->
            instructions.add(singleInput.split(" ").let { Instruction(it[0], it[1].toInt(), index) })
        }

        var shouldExecuteLoop = true
        var currentIndex = 0

        while(shouldExecuteLoop) {
            instructions[currentIndex].let {
                if (it.executionCount >= 1) {
                    shouldExecuteLoop = false
                    return@let
                }

                when (it.instruction) {
                    "acc" -> {
                        accumulator += it.amount
                        currentIndex += 1
                    }
                    "jmp" -> {
                        currentIndex += it.amount
                    }
                    "nop" -> {
                        currentIndex += 1
                    }
                }

                it.executionCount += 1
            }
        }

        print("Accumulator value is: $accumulator")
        print("\nDONE :D")
    }

    override fun solution2(input: List<String>) {
        var accumulator = 0

        val instructions = mutableListOf<Instruction>()
        input.forEachIndexed { index, singleInput ->
            instructions.add(singleInput.split(" ").let { Instruction(it[0], it[1].toInt(), index) })
        }

        var shouldExecuteLoop = true
        var currentIndex = 0

        instructions.mapNotNull { if (it.instruction == "nop" || it.instruction == "jmp") it else null }.forEach {
            val originalInstruction = if (it.instruction == "nop") {
                it.instruction = "jmp"
                "nop"
            } else {
                it.instruction = "nop"
                "jmp"
            }

            while(shouldExecuteLoop) {
                if (currentIndex == instructions.size) {
                    shouldExecuteLoop = false
                    print("Accumulator value is: $accumulator")
                    print("\nDONE :D")
                    return
                }

                instructions[currentIndex].let {
                    if (it.executionCount >= 1) {
                        shouldExecuteLoop = false
                        return@let
                    }

                    when (it.instruction) {
                        "acc" -> {
                            accumulator += it.amount
                            currentIndex += 1
                        }
                        "jmp" -> {
                            currentIndex += it.amount
                        }
                        "nop" -> {
                            currentIndex += 1
                        }
                    }

                    it.executionCount += 1
                }
            }

            accumulator = 0
            shouldExecuteLoop = true
            currentIndex = 0
            instructions.forEach { it.executionCount = 0 }
            it.instruction = originalInstruction
        }
    }
}

data class Instruction(
    var instruction: String,
    val amount: Int,
    var instructionIndex: Int,
    var executionCount: Int = 0
)