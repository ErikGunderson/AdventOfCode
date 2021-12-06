import solutions.aoc2021.Day5
import solutions.aoc2021.Day6

fun main() {
    try {
        Day6().runProblem(Solution.ONE)
        Day6().runProblem(Solution.TWO)
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}