package solutions.utils

import java.io.File
import java.io.PrintWriter

fun main() {
    SetupNewYear.setupYear("2023")
}

class SetupNewYear {
    companion object {
        fun setupYear(year: String) {
            val solutionsDirectoryPath = System.getProperty("user.dir") +
                    File.separator + "src" +
                    File.separator + "main" +
                    File.separator + "kotlin" +
                    File.separator + "solutions" +
                    File.separator + "aoc$year"
            val inputsDirectoryPath = System.getProperty("user.dir") +
                    File.separator + "inputs" +
                    File.separator + year

            File(solutionsDirectoryPath).mkdirs()
            File(inputsDirectoryPath).mkdirs()

            (1..25).forEach { day ->
                File(solutionsDirectoryPath + File.separator + "Day$day.kt").let {
                    if (!it.exists()) {
                        it.createNewFile()

                        PrintWriter(it).run {
                            print(
                                """
                                package solutions.aoc$year

                                import solutions.utils.AoCProblem

                                fun main() {
                                    Day$day().runAllSolutions()
                                }

                                class Day$day: AoCProblem() {
                                    fun solution1(input: List<String>) {

                                    }

                                    fun solution2(input: List<String>) {

                                    }
                                }
                                """.trimIndent()
                            )

                            close()
                        }
                    }
                }

                File(inputsDirectoryPath + File.separator + "Day$day.txt").let {
                    if (!it.exists()) it.createNewFile()
                }
            }
        }
    }
}