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

abstract class AoC2020Problem: FileReader() {
    override val year = "2020"
}

abstract class AoC2021Problem: FileReader() {
    override val year = "2021"
}