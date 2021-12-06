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

        if (solution == Solution.ONE) solution1() else solution2()

        print("\nCompleted Solution! :D")
        print("\nExecution time: ${System.currentTimeMillis() - perfTiming}")
    }

    protected abstract fun solution1()
    protected abstract fun solution2()
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