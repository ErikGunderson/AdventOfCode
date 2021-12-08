import solutions.aoc2021.Day7
import solutions.aoc2021.Day8

fun main() {
    try {
        Day8().runAllSolutions()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}