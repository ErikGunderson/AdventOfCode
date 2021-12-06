import solutions.aoc2021.Day5

fun main() {
    try {
        Day5().runProblem(Solution.TWO)
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}