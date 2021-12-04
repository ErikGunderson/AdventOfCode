import solutions.aoc2021.Day1
import solutions.aoc2021.Day2
import solutions.aoc2021.Day3

fun main() {
    try {
        Day3().solution2()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}