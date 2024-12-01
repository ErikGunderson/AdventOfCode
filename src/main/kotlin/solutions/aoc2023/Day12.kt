package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day12().runAllSolutions()
}

//
//1
//5 6
//47 82 121

class Day12: AoCProblem() {
    fun solution1(input: List<String>) {
        val sortedNumbers = Regex("\\d*(?=\\s)").let { regex ->
            input.map { row -> regex.find(row)!!.value.toInt() }.sorted()
        }

        val leftHandNumbers = (0 steppedRangeTo sortedNumbers.lastIndex).map { nextIndex -> sortedNumbers[nextIndex] }

        println("left hand numbers: ${leftHandNumbers.joinToString(" ")}")
    }

//    data class User(val age: Int, val hasSubscription: Boolean)
//    var user: User? = User(22, true)
//    fun log(logString: String)
//
//    fun solution2(input: List<String>) {
//        user?.let { user ->
//            when {
//                !user.hasSubscription -> log("user has no subscription")
//                user.age >= 18 -> showFullVersion()
//                else -> showChildrenVersion()
//            }
//        }
//
//    }
}

class SteppingRange(
    override val start: Int,
    override val endInclusive: Int
) : ClosedRange<Int>, Iterable<Int> {

    override fun iterator(): Iterator<Int> = PointIterator(start, endInclusive)
}

class PointIterator(start: Int, private val endInclusive: Int) : Iterator<Int> {

    private var currentValue = start
    private var currentIncrement = 0

    override fun hasNext(): Boolean {
        return currentValue + currentIncrement <= endInclusive
    }

    override fun next(): Int = (currentValue + currentIncrement).also {
        currentValue += currentIncrement
        currentIncrement++
    }
}

private infix fun Int.steppedRangeTo(that: Int): SteppingRange = SteppingRange(this, that)
