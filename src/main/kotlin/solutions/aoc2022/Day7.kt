package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day7().runAllSolutions()
}

class Day7: AoCProblem() {
    fun solution1(input: List<String>) {
            val filesystem = parseFilesystemTree(input)

            var sumOfDirectorySizes: Long = 0
            filesystem.calculateSize { directory, size -> if (size < 100000) sumOfDirectorySizes += size }

            print("Sum of directories sizes with size < 100,000 :: $sumOfDirectorySizes")
        }

        fun solution2(input: List<String>) {
            val filesystem = parseFilesystemTree(input)

            val rootDirectorySize = filesystem.calculateSize { directory, size -> /* nothing for first pass */ }
        val sizeNeededForUpdate = 30000000 - (70000000 - rootDirectorySize)

        val upForDeletion = mutableListOf<Long>()
        filesystem.calculateSize { directory, size -> if (size >= sizeNeededForUpdate) upForDeletion.add(size) }

        print("Smallest directory to delete to make room for update :: ${upForDeletion.minOrNull()}")
    }

    private fun parseFilesystemTree(input: List<String>) : Directory {
        val root = Directory("/", null)
        var currentNode = root

        input.forEach {
            it.split(" ").let { command ->
                when (command[0]) {
                    "$" -> if (command[1] == "cd" && command[2] != "/") {
                        currentNode = if (command[2] == "..") currentNode.getParentDirectory() else currentNode.changeDirectory(command[2])
                    } /* No else, skip ls command */
                    "dir" -> currentNode.addDirectory(command[1])
                    else -> /*File*/ currentNode.addFile(command[1], command[0].toLong())
                }
            }
        }

        return root
    }
}

typealias Visitor = (Directory, Long) -> Unit

interface Node {
    fun calculateSize(visit: Visitor): Long
}

class Directory(private val directoryName: String, private val parent: Directory?) : Node {
    private val children: MutableList<Node> = mutableListOf()

    fun changeDirectory(newDirectory: String) = children.first { (it as? Directory)?.directoryName == newDirectory } as Directory

    fun addDirectory(directoryName: String) {
        children.add(Directory(directoryName, this))
    }

    fun addFile(fileName: String, fileSize: Long) {
        children.add(File(fileName, fileSize))
    }

    fun getParentDirectory() = parent!!

    override fun calculateSize(visit: Visitor): Long {
        return children.sumOf { it.calculateSize(visit) }.also { visit(this, it) }
    }
}

class File(private val fileName: String, private val fileSize: Long) : Node {
    override fun calculateSize(visit: Visitor): Long = fileSize
}