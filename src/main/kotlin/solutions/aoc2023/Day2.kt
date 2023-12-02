package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day2().runAllSolutions()
}

class Day2 : AoCProblem() {
    override val testInput = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )

    fun solution1(input: List<String>) {
        val possibleGameIds = mutableSetOf<Int>()

        input.map { game ->
            game.split(": ").let { (gameId, gameData) ->
                gameData.split("; ").map { round ->
                    round.split(", ").map { shownCubes ->
                        shownCubes.split(" ").let { shownCubeData ->
                            CubeCount(shownCubeData[0].toInt(), shownCubeData[1].toCubeColor())
                        }
                    }
                }.let { gameRounds ->
                    if (gameRounds.any { round -> round.any { cubes -> cubes.overMax() } }) {
                        print("More cubes shown than possible in game $gameId\n")
                    } else {
                        possibleGameIds += gameId.split(" ")[1].toInt()
                    }
                }
            }
        }

        print("Game ID sum where max was not exceeded is ${possibleGameIds.sum()}\n")
    }

    fun solution2(input: List<String>) {
        var gamePowerSum = 0L

        input.map { game ->
            game.split(": ").let { (gameId, gameData) ->
                gameData.split("; ").map { round ->
                    round.split(", ").map { shownCubes ->
                        shownCubes.split(" ").let { shownCubeData ->
                            CubeCount(shownCubeData[0].toInt(), shownCubeData[1].toCubeColor())
                        }
                    }
                }.flatten().groupBy { it.color }.map { groupedCubeCounts ->
                    groupedCubeCounts.value.maxBy { it.count }
                }.let { minimumCubeCounts ->
                    print("for $gameId minimum cube counts are $minimumCubeCounts and game power is ${minimumCubeCounts.getGamePower()}\n")
                    gamePowerSum += minimumCubeCounts.getGamePower()
                }
            }
        }

        print("Game power sum is $gamePowerSum")
    }
}

enum class CubeColor {
    RED, BLUE, GREEN
}

fun String.toCubeColor(): CubeColor = when (this) {
    "red" -> CubeColor.RED
    "blue" -> CubeColor.BLUE
    "green" -> CubeColor.GREEN
    else -> throw RuntimeException("BAD CUBE COLOR >:[]")
}

data class CubeCount(val count: Int, val color: CubeColor) {
    private val max
        get() = when (color) {
            CubeColor.RED -> 12
            CubeColor.BLUE -> 14
            CubeColor.GREEN -> 13
        }

    fun overMax() = count > max
}

fun List<CubeCount>.getGamePower(): Long = this.fold(1L) { acc, nextElement -> acc * nextElement.count }