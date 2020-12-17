import solutions.*

fun main() {
    try {
        Day15().solution1()
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.map { "\nat -> $it" }.joinToString("")} \n")
    }
}