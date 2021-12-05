import solutions.aoc2021.Day5

fun main() {
    try {
        Day5().solution2()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}