import solutions.aoc2021.Day7

fun main() {
    try {
        Day7().runAllSolutions()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}