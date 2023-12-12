package solutions.aoc2020

import solutions.utils.AoCProblem

class Day13 : AoCProblem() {
    fun solution1(input: List<String>) {
        input.let {
            val targetTime = it[0].toInt()
            val busIds = it[1].split(",").mapNotNull {
                if (it == "x") null
                else it.toInt()
            }

            var bestBus = 0 to Int.MAX_VALUE
            busIds.forEach { busId ->
                var currentArrival = busId
                while (currentArrival < targetTime) {
                    currentArrival += busId
                }
                (currentArrival - targetTime).let { difference ->
                    if (difference < bestBus.second) bestBus = busId to difference
                }
            }

            print("earliest bus ID: ${bestBus.first} minutes waiting is: ${bestBus.second} multipled is: " +
                    "${bestBus.first * bestBus.second}")
            print("\nDONE :D")
        }
    }

    /**
     * https://www.geeksforgeeks.org/chinese-remainder-theorem-set-1-introduction/
     */
    fun solution2(input: List<String>) {
        val buses = input.let {
            it[1].split(",").mapIndexed { index, busId ->
                busId to index
            }
        }.filter { it.first != "x" }.map { Bus(it.first.toInt(), it.first.toInt() - it.second) }.toMutableList()

        var formulaSum = 0L
        val busIdProduct = buses.busIdProduct()
        buses.forEach {
            val pp = busIdProduct / it.busId
            formulaSum += (it.requiredOffset * pp * modInverse(pp, it.busId)!!)
        }

        val crtValue = formulaSum % busIdProduct

        print("earliest valid timestamp: $crtValue")
        print("\nDONE :D")
    }

    private fun findGcdExtended(a: Long, b:Long, params: GcdParams) : Long {
        if (a == 0L) {
            params.x = 0
            params.y = 1
            return b
        }

        val recursiveParams = GcdParams(0, 0)
        val gcd = findGcdExtended(b % a, a, recursiveParams)

        params.x = recursiveParams.y - (b / a) * recursiveParams.x
        params.y = recursiveParams.x

        return gcd
    }

    private fun modInverse(a: Long, m: Int) : Long? {
        val params = GcdParams(0, 0)
        val gcd = findGcdExtended(a, m.toLong(), params)

        return if (gcd != 1L) {
            print("\nError - Inverse does not exist!")
            null
        } else {
            return (params.x % m + m) % m
        }
    }
}

private data class Bus(
    val busId: Int,
    val requiredOffset: Int
)

private data class GcdParams(
    var x: Long,
    var y: Long
)

private fun List<Bus>.busIdProduct() : Long {
    var product = 1L
    this.forEach { product *= it.busId }
    return product
}