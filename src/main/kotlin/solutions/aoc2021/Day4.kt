package solutions.aoc2021

import AoC2021Problem

class Day4 : AoC2021Problem() {
    override fun solution1() {
        val numbersDrawn = mutableListOf<Int>()
        var boardNumber = 0
        val boardNumbers = Array(100) { index -> BoardNumber(index) }
        val boards = mutableMapOf<Int, MutableList<List<BoardNumber>>>()

        //Assemble boards
        inputFile.readLines().map { it.trim() }.let { inputLines ->
            inputLines.forEachIndexed { index, inputLine ->
                when {
                    index == 0 -> numbersDrawn.addAll(inputLine.split(",").map { it.trim().toInt() })
                    inputLine.isBlank() -> {
                        if (boardNumber != 0) {
                            val rows = boards[boardNumber]!!
                            val columns = mutableListOf<List<BoardNumber>>()
                            for (rowNumber in 0..rows.lastIndex) {
                                columns.add(rows.map { it[rowNumber] })
                            }
                            boards[boardNumber]!!.addAll(columns)
                        }

                        boardNumber++
                        boards[boardNumber] = mutableListOf()
                    }
                    else -> {
                        val numbersInRow = inputLine.split(Regex("\\s+(?=\\d)")).map { it.toInt() }
                        boards[boardNumber]!!.add(numbersInRow.map { boardNumbers[it] })

                        if (index == inputLines.lastIndex) {
                            val rows = boards[boardNumber]!!
                            val columns = mutableListOf<List<BoardNumber>>()
                            for (rowNumber in 0..rows.lastIndex) {
                                columns.add(rows.map { it[rowNumber] })
                            }
                            boards[boardNumber]!!.addAll(columns)
                        }
                    }
                }
            }
        }

        //Play game until a board wins
        numbersDrawn.forEach playGame@{ currentNumber ->
            boardNumbers[currentNumber].marked = true

            boards.values.forEach { board ->
                board.forEach { rowOrColumn ->
                    if (rowOrColumn.all { it.marked }) {
                        //calculate score
                        val boardScore = board.flatten().distinctBy { it.value }.filterNot { it.marked }.map { it.value }.sum()

                        print("Board score is $boardScore, final number called is $currentNumber, product is ${boardScore * currentNumber}")
                        return
                    }
                }
            }
        }
    }

    override fun solution2() {
        val numbersDrawn = mutableListOf<Int>()
        var boardNumber = 0
        val boardNumbers = Array(100) { index -> BoardNumber(index) }
        val boards = mutableMapOf<Int, MutableList<List<BoardNumber>>>()

        //Assemble boards
        inputFile.readLines().map { it.trim() }.let { inputLines ->
            inputLines.forEachIndexed { index, inputLine ->
                when {
                    index == 0 -> numbersDrawn.addAll(inputLine.split(",").map { it.trim().toInt() })
                    inputLine.isBlank() -> {
                        if (boardNumber != 0) {
                            val rows = boards[boardNumber]!!
                            val columns = mutableListOf<List<BoardNumber>>()
                            for (rowNumber in 0..rows.lastIndex) {
                                columns.add(rows.map { it[rowNumber] })
                            }
                            boards[boardNumber]!!.addAll(columns)
                        }

                        boardNumber++
                        boards[boardNumber] = mutableListOf()
                    }
                    else -> {
                        val numbersInRow = inputLine.split(Regex("\\s+(?=\\d)")).map { it.toInt() }
                        boards[boardNumber]!!.add(numbersInRow.map { boardNumbers[it] })

                        if (index == inputLines.lastIndex) {
                            val rows = boards[boardNumber]!!
                            val columns = mutableListOf<List<BoardNumber>>()
                            for (rowNumber in 0..rows.lastIndex) {
                                columns.add(rows.map { it[rowNumber] })
                            }
                            boards[boardNumber]!!.addAll(columns)
                        }
                    }
                }
            }
        }

        //Play game until a board wins
        numbersDrawn.forEach playGame@{ currentNumber ->
            boardNumbers[currentNumber].marked = true

            val boardsToRemove = mutableListOf<Int>()
            boards.entries.forEach { (boardNumber, board) ->
                board.forEach { rowOrColumn ->
                    if (rowOrColumn.all { it.marked }) {
                        //Only 1 board remained, and it won!
                        if (boards.size == 1) {
                            //calculate score
                            val boardScore = board.flatten().distinctBy { it.value }.filterNot { it.marked }.map { it.value }.sum()

                            print("Board score is $boardScore, final number called is $currentNumber, product is ${boardScore * currentNumber}")
                            return
                        } else {
                            boardsToRemove.add(boardNumber)
                        }
                    }
                }
            }
            boardsToRemove.forEach { boards.remove(it) }
        }
    }
}

data class BoardNumber(
    val value: Int,
    var marked: Boolean = false
)