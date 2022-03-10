package solutions.aoc2020

import AoC2020Problem

class Day1: AoC2020Problem() {
    override fun solution1(input: List<String>) {
        val partitionedInputs = input.map { it.toInt() }.partition { it >= 1010 }
        val largeInputs = partitionedInputs.first
        val smallInputs = partitionedInputs.second.sorted()

        largeInputs.forEach { largeInput ->
            val smallIndex = smallInputs.binarySearch { (largeInput + it) - 2020 }

            if (smallIndex > 0) {
                //we found it!
                print("first number is $largeInput, second number is ${smallInputs[smallIndex]}, multiplied they equal ${largeInput * smallInputs[smallIndex]}")
                print("\nDONE :D")
            }
        }
    }

    override fun solution2(input: List<String>) {
        val inputs = input.map { it.toInt() }.sorted()

        inputs.forEachIndexed { index, singleInput ->
            var smallerIndex = index - 1
            var largerIndex = index + 1

            while (smallerIndex >= 0 && largerIndex <= inputs.lastIndex) {
                val computedValue = singleInput + inputs[smallerIndex] + inputs[largerIndex]

                when {
                    computedValue == 2020 -> {
                        print(
                            "first number is $singleInput, " +
                                    "second number is ${inputs[smallerIndex]}, " +
                                    "third number is ${inputs[largerIndex]} " +
                                    "multiplied they equal ${singleInput * inputs[smallerIndex] * inputs[largerIndex]}")
                        print("\nDONE :D")
                        return
                    }
                    computedValue > 2020 -> {
                        smallerIndex -= 1
                    }
                    computedValue < 2020 -> {
                        largerIndex += 1
                    }
                }
            }
        }
    }
}