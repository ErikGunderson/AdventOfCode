import java.io.File
import kotlin.system.exitProcess

abstract class FileReader {
    val inputFile
        get() = getFile()

    private fun getFile(): File {
        val inputFileName = this.javaClass.simpleName
        val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
        return File(inputFileLocation).let { if (it.exists()) it else { print("Input file does not exist!");  exitProcess(99) } }
    }
}