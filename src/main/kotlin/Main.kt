import solutions.aoc2021.Day8

fun main() {
    try {
        Day8().runAllSolutions()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.joinToString("") { "\nat -> $it" }} \n")
    }
}