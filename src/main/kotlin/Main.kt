import solutions.aoc2021.Day4

fun main() {
    try {
        Day4().solution2()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}