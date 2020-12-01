import java.io.File

fun day1Solution1() {
    val inputFileName = "puzzle1"
    val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
    val inputFile = File(inputFileLocation)

    if (!inputFile.exists()) { print("Input file does not exist!"); return }

    val partitionedInputs = inputFile.readLines().map { it.trim() }.map { it.toInt() }.partition { it >= 1010 }
    val largeInputs = partitionedInputs.first
    val smallInputs = partitionedInputs.second.sorted()

    largeInputs.forEach { largeInput ->
        val smallIndex = smallInputs.binarySearch { (largeInput + it) - 2020 }

        if (smallIndex > 0) {
            //we found it!
            print("first number is $largeInput, second number is ${smallInputs[smallIndex]}, multiplied they equal ${largeInput * smallInputs[smallIndex]}")
            print("\nDONE :D")
        }
    }
}

fun day1Solution2() {
    val inputFileName = "puzzle1"
    val inputFileLocation = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + inputFileName
    val inputFile = File(inputFileLocation)

    if (!inputFile.exists()) { print("Input file does not exist!"); return }

//    val partitionedInputs = inputFile.readLines().map { it.trim() }.map { it.toInt() }.partition { it >= 1010 }
//    val largeInputs = partitionedInputs.first
//    val smallInputs = partitionedInputs.second.sorted()
//
//    largeInputs.forEach { largeInput ->
//        val smallIndex = smallInputs.binarySearch { (largeInput + it) - 2020 }
//
//        if (smallIndex > 0) {
//            //we found it!
//            print("first number is $largeInput, second number is ${smallInputs[smallIndex]}, multiplied they equal ${largeInput * smallInputs[smallIndex]}")
//            print("\nDONE :D")
//        }
//    }
}