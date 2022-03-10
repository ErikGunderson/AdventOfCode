import solutions.aoc2021.Day9

fun main() {
    try {
        Day9().runAllSolutions()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.joinToString("") { "\nat -> $it" }} \n")
    }
}