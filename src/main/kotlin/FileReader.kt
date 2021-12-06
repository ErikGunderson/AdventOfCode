import java.io.File
import kotlin.system.exitProcess

abstract class FileReader {
    val inputFile
        get() = getFile()

    abstract val year: String

    private fun getFile(): File {
        val inputFileName = "${this.javaClass.simpleName}.txt"
        val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + year + File.separator + inputFileName
        return File(inputFileLocation).let { if (it.exists()) it else { print("Input file does not exist!");  exitProcess(99) } }
    }
}

abstract class AoCProblem: FileReader() {
    fun runProblem(solution: Solution){
        val perfTiming = System.currentTimeMillis()

        val input = inputFile.readLines().map { it.trim() }
        if (solution == Solution.ONE) solution1(input) else solution2(input)

        print("\nCompleted Solution! :D")
        print("\nExecution time: ${System.currentTimeMillis() - perfTiming}")
    }

    protected abstract fun solution1(input: List<String>)
    protected abstract fun solution2(input: List<String>)
}

abstract class AoC2020Problem: AoCProblem() {
    override val year = "2020"
}

abstract class AoC2021Problem: AoCProblem() {
    override val year = "2021"
}

enum class Solution {
    ONE,
    TWO
}