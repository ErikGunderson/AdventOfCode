package solutions.aoc2021

import AoC2021Problem

class Day3: AoC2021Problem() {
    override fun solution1(input: List<String>) {
        val counters = Array(12) { index -> PositionalBitCounter(index)}
        input.map { it.reversed() }.forEach {
            it.forEachIndexed { index, bit ->
                val positionCounter = counters[index]

                if (bit == '0') positionCounter.zeros += 1
                else positionCounter.ones += 1
            }
        }

        val gammaRate = Integer.parseInt(counters.map { if (it.ones > it.zeros) '1' else '0' }.reversed().joinToString(""), 2)
        val epsilonRate = Integer.parseInt(counters.map { if (it.ones > it.zeros) '0' else '1' }.reversed().joinToString(""), 2)

        print("Gamma rate is $gammaRate, epsilon rate is $epsilonRate, product is ${gammaRate * epsilonRate}")
    }

    override fun solution2(input: List<String>) {
        val oxygenRating = Integer.parseInt(evaluateDiagnostic(input, 0, Frequency.MOST), 2)
        val co2Rating = Integer.parseInt(evaluateDiagnostic(input, 0, Frequency.LEAST), 2)

        print("Oxygen Generator Rating is $oxygenRating, CO2 Scrubber Rating is $co2Rating, product is ${oxygenRating * co2Rating}")
    }

    private fun evaluateDiagnostic(inputs: List<String>, index: Int, frequency: Frequency): String {
        val groupedInputs = inputs.groupBy { it[index] }

        return when (frequency) {
            Frequency.MOST -> {
                val targetSet = groupedInputs.maxByOrNull { (_, values) -> values.size }!!.value

                if (targetSet.size == 1) targetSet.first()
                else evaluateDiagnostic(targetSet, index + 1, frequency)
            }
            Frequency.LEAST -> {
                val targetSet = groupedInputs.minByOrNull { (_, values) -> values.size }!!.value

                if (targetSet.size == 1) targetSet.first()
                else evaluateDiagnostic(targetSet, index + 1, frequency)
            }
        }
    }

    enum class Frequency {
        MOST,
        LEAST
    }
}

data class PositionalBitCounter(
    val index: Int = 0,
    var ones: Int = 0,
    var zeros: Int = 0
)