package solutions.utils

import java.io.File
import java.lang.reflect.Method
import kotlin.system.exitProcess

private const val SOLUTIONS_DIR = "solutions"
private const val INPUTS_DIR = "inputs"

abstract class FileReader {
    val inputFile: File
        get() = getFile()

    private val year: String
        get() = this.javaClass.packageName.removePrefix("$SOLUTIONS_DIR.aoc")

    private fun getFile(): File {
        val inputFileName = "${this.javaClass.simpleName}.txt"
        val inputFileLocation = listOf(System.getProperty("user.dir"), INPUTS_DIR, year, inputFileName).joinToString(File.separator)
        return File(inputFileLocation).let { if (it.exists()) it else { print("Input file does not exist!");  exitProcess(99) } }
    }
}

abstract class AoCProblem: FileReader() {
    private val input get() = inputFile.readLines().map { it.trim() }

    fun runAllSolutions() {
        this.javaClass.declaredMethods.filter { it.name.contains("solution") }.forEach {
            it.run(this, input)
        }
    }

    fun runSolution(solutionNumber: Int) {
        this.javaClass.declaredMethods.filter { it.name.contains("solution$solutionNumber") }.forEach {
            it.run(this, input)
        }
    }
}

private fun Method.run(parentClass: AoCProblem, input: List<String>) {
    try {
        val perfTiming = System.currentTimeMillis()

        this.invoke(parentClass, input)

        print("\nCompleted Solution! :D")
        print("\nExecution time: ${System.currentTimeMillis() - perfTiming}ms\n\n")
    } catch (exception: Exception) {
        print("\n$exception ${exception.stackTrace.joinToString("") { "\nat -> $it" }} \n")
    }
}