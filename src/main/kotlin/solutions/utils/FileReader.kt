package solutions.utils

import java.io.File
import java.lang.reflect.InvocationTargetException
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
    protected val untrimmedInput get() = inputFile.readLines()

    protected open val testInput: List<String> = emptyList()

    fun runAllSolutions() {
        this.javaClass.declaredMethods.filter { Regex("solution\\d$").containsMatchIn(it.name) }
            .sortedBy { it.name }
            .forEach { it.run(this, input)
        }
    }

    fun runSolution(solutionNumber: Int) {
        this.javaClass.declaredMethods.filter { Regex("solution$solutionNumber$").containsMatchIn(it.name) }.forEach {
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
    } catch (exception: InvocationTargetException) {
        print("\n$exception ${exception.cause!!.stackTrace.joinToString("") { "\nat -> $it" }} \n")
    }
}