import solutions.aoc2021.Day1
import solutions.aoc2021.Day2

fun main() {
    try {
        Day2().solution2()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}