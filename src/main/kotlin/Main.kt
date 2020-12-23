import solutions.*

fun main() {
    try {
        Day21().solution2()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}