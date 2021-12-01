import solutions.aoc2021.Day1

fun main() {
    try {
        Day1().solution2()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}