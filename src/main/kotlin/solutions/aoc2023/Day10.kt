package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day10().runSolution(2)
}

class Day10 : AoCProblem() {

    override val testInput: List<String>
        get() = listOf(
            "..F7.",
            ".FJ|.",
            "SJ.L7",
            "|F--J",
            "LJ..."
        )

    fun solution1(input: List<String>) = input.let { pipeMap ->
        val startingCoordinate = pipeMap.indexOfFirst { it.contains('S') }.let { yIndex ->
            PipeCoordinate('S', pipeMap[yIndex].indexOf('S'), yIndex)
        }

        val traversalOne = mutableListOf(startingCoordinate)
        val traversalTwo = mutableListOf(startingCoordinate)
        var stepCount = 1

        findStartingPipes(startingCoordinate, pipeMap, traversalOne, traversalTwo)

        //traverse pipe loop
        while (traversalOne.last() != traversalTwo.last()) {
            traversalOne.let { traversal ->
                traversal.add(traversal.last().findNextCoordinate(traversal[traversal.lastIndex - 1], pipeMap))
            }

            traversalTwo.let { traversal ->
                traversal.add(traversal.last().findNextCoordinate(traversal[traversal.lastIndex - 1], pipeMap))
            }

            stepCount++
        }

        println("Furthest position from start is ${traversalOne.last()} and step count to that position is $stepCount")
    }

    fun solution2(input: List<String>) = input.let { pipeMap ->
        val traversalLoop = mutableListOf<PipeCoordinate>().apply {
            pipeMap.indexOfFirst { it.contains('S') }.let { yIndex ->
                addAll(findStartingPipes(pipeMap[yIndex].indexOf('S') to yIndex, pipeMap))
            }
        }

        //traverse pipe loop
        while (traversalLoop.last().pipe != 'S') {
            traversalLoop.let { traversal ->
                traversal.add(traversal.last().findNextCoordinate(traversal[traversal.lastIndex - 1], pipeMap))
            }
        }

        val pipesByYCoordinate = traversalLoop.groupBy { it.y }

        //scan rows
        //https://en.wikipedia.org/wiki/Nonzero-rule
        var enclosedCells = 0
        pipeMap.forEachIndexed { yCoordinate, row ->
            var windingNumber = 0

            row.forEachIndexed { xCoordinate, cell ->
                pipesByYCoordinate[yCoordinate]?.firstOrNull { it.x == xCoordinate }?.let { loopPipe ->
                    //We found a previously identified loop cell, maybe change score
                    when (loopPipe.step) {
                        Step.Up -> windingNumber++
                        Step.Down -> windingNumber--
                        else -> { /* NO OP */ }
                    }
                } ?: run {
                    //On a cell not on the loop; check score
                    if (windingNumber != 0) enclosedCells++
                }
            }
        }

        println("Total number of enclosed cells is $enclosedCells")
    }

    private fun findStartingPipes(
        startingCoordinate: PipeCoordinate,
        input: List<String>,
        traversalOne: MutableList<PipeCoordinate>,
        traversalTwo: MutableList<PipeCoordinate>
    ) {
        val validXRange = 0..input[0].lastIndex
        val validYRange = 0..input.lastIndex

        //starting positions for both traversals
        //Check North
        (startingCoordinate.x to startingCoordinate.y - 1).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('|', '7', 'F')) {
                        traversalOne.add(PipeCoordinate(char, x, y))
                    }
                }
            }
        }

        //Check East
        (startingCoordinate.x + 1 to startingCoordinate.y).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('-', '7', 'J')) {
                        PipeCoordinate(char, x, y).let { pipe ->
                            if (traversalOne.size == 1) traversalOne.add(pipe) else traversalTwo.add(pipe)
                        }
                    }
                }
            }
        }

        //Check South
        (startingCoordinate.x to startingCoordinate.y + 1).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('|', 'L', 'J')) {
                        PipeCoordinate(char, x, y).let { pipe ->
                            if (traversalOne.size == 1) traversalOne.add(pipe) else traversalTwo.add(pipe)
                        }
                    }
                }
            }
        }

        //Check West
        (startingCoordinate.x - 1 to startingCoordinate.y).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('-', 'L', 'F')) {
                        PipeCoordinate(char, x, y).let { pipe ->
                            if (traversalOne.size == 1) traversalOne.add(pipe) else traversalTwo.add(pipe)
                        }
                    }
                }
            }
        }
    }

    private fun findStartingPipes(startingCoordinate: Pair<Int, Int>, input: List<String>): List<PipeCoordinate> {
        val validXRange = 0..input[0].lastIndex
        val validYRange = 0..input.lastIndex

        val firstConnectingPipes = mutableListOf<PipeCoordinate>()

        //starting positions for both traversals
        //Check North
        (startingCoordinate.first to startingCoordinate.second - 1).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('|', '7', 'F')) {
                        firstConnectingPipes.add(PipeCoordinate(char, x, y, Step.Up))
                    }
                }
            }
        }

        //Check East
        (startingCoordinate.first + 1 to startingCoordinate.second).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('-', '7', 'J')) {
                        firstConnectingPipes.add(PipeCoordinate(char, x, y, Step.None))
                    }
                }
            }
        }

        //Check South
        (startingCoordinate.first to startingCoordinate.second + 1).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('|', 'L', 'J')) {
                        firstConnectingPipes.add(PipeCoordinate(char, x, y, Step.Down))
                    }
                }
            }
        }

        //Check West
        (startingCoordinate.first - 1 to startingCoordinate.second).let { (x, y) ->
            if (x in validXRange && y in validYRange) {
                input[y][x].let { char ->
                    if (char in listOf('-', 'L', 'F')) {
                        firstConnectingPipes.add(PipeCoordinate(char, x, y, Step.None))
                    }
                }
            }
        }

        val startingPipeStep = if (
            firstConnectingPipes.all { it.x == startingCoordinate.first } ||
            firstConnectingPipes.first().y == startingCoordinate.second
        ) {
            Step.Up
        } else {
            Step.Down
        }

        return mutableListOf(
            PipeCoordinate(
                'S',
                startingCoordinate.first,
                startingCoordinate.second,
                startingPipeStep
            ),
            firstConnectingPipes.first()
        )
    }
}

private fun PipeCoordinate.findNextCoordinate(previousCoordinate: PipeCoordinate, input: List<String>): PipeCoordinate {
    val xCoordinate: Int
    val yCoordinate: Int

    when (this.pipe) {
        '|' -> {
            xCoordinate = this.x
            yCoordinate = this.y + (this.y - previousCoordinate.y)
        }
        '-' -> {
            xCoordinate = this.x + (this.x - previousCoordinate.x)
            yCoordinate = this.y
        }
        'L', '7' -> {
            xCoordinate = this.x + (this.y - previousCoordinate.y)
            yCoordinate = this.y + (this.x - previousCoordinate.x)
        }
        'J', 'F' -> {
            xCoordinate = this.x - (this.y - previousCoordinate.y)
            yCoordinate = this.y - (this.x - previousCoordinate.x)
        }
        else -> throw RuntimeException("Dead end found in pipe traversal")
    }

    val nextPipe = input[yCoordinate][xCoordinate]
    val step: Step = if (nextPipe in listOf('|', '7', 'F')) {
        when {
            yCoordinate - this.y == 0 -> Step.Down
            yCoordinate - this.y > 0 -> Step.Down
            else -> Step.Up
        }
    } else {
        Step.None
    }

    return PipeCoordinate(nextPipe, xCoordinate, yCoordinate, step)
}

private data class PipeCoordinate(
    val pipe: Char,
    val x: Int,
    val y: Int,
    val step: Step = Step.None
)

private enum class Step {
    Up, Down, None
}