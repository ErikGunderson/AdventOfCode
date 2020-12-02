import java.io.File

fun day2Solution1() {
    val inputFileName = "puzzle2"
    val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
    val inputFile = File(inputFileLocation)

    if (!inputFile.exists()) { print("Input file does not exist!"); return }

    var validPasswordCount = 0

    inputFile.readLines().map { it.trim() }.forEach { input ->
        input.split(":", limit = 2).let {
            val policy = it[0].split(" ").let {
                it[0] to it[1].first()
            }

            val policyRange = policy.first.split("-").map { it.toInt() }
            val password = it[1].trim()

            val importantCharCount = password.count { it == policy.second }
            if (importantCharCount >= policyRange[0] && importantCharCount <= policyRange[1]) validPasswordCount += 1
        }
    }

    print("Valid password count is $validPasswordCount")
    print("\nDONE :D")
}

fun day2Solution2() {
    val inputFileName = "puzzle2"
    val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
    val inputFile = File(inputFileLocation)

    if (!inputFile.exists()) { print("Input file does not exist!"); return }

    var validPasswordCount = 0

    inputFile.readLines().map { it.trim() }.forEach { input ->
        input.split(":", limit = 2).let {
            val policy = it[0].split(" ").let {
                it[0] to it[1].first()
            }

            val policyIndices = policy.first.split("-").map { it.toInt() - 1 }
            val password = it[1].trim()

            val isImportantCharAtFirstIndex = password[policyIndices[0]] == policy.second
            val isImportantCharAtSecondIndex = password[policyIndices[1]] == policy.second

            if (isImportantCharAtFirstIndex xor isImportantCharAtSecondIndex) validPasswordCount += 1
        }
    }

    print("Valid password count is $validPasswordCount")
    print("\nDONE :D")
}