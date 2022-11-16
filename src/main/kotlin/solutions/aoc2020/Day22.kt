package solutions.aoc2020

import solutions.utils.AoCProblem
import java.util.*

class Day22 : AoCProblem() {
    fun solution1(input: List<String>) {
        val playerOneDeck = LinkedList<Int>()
        val playerTwoDeck = LinkedList<Int>()
        var currentDeck = 1

        input.forEach {
            when {
                it.contains("Player") -> return@forEach
                it.isBlank() -> {
                    currentDeck = 2
                    return@forEach
                }
                else -> {
                    if (currentDeck == 1) {
                        playerOneDeck.addLast(it.toInt())
                    } else {
                        playerTwoDeck.addLast(it.toInt())
                    }
                }
            }
        }

        var turns = 0
        while (playerOneDeck.isNotEmpty() && playerTwoDeck.isNotEmpty()) {
            val playerOneCard = playerOneDeck.pop()
            val playerTwoCard = playerTwoDeck.pop()

            if (playerOneCard > playerTwoCard) {
                playerOneDeck.addLast(playerOneCard)
                playerOneDeck.addLast(playerTwoCard)
            } else {
                playerTwoDeck.addLast(playerTwoCard)
                playerTwoDeck.addLast(playerOneCard)
            }

            turns += 1
            print("turn $turns completed\n")
        }

        val winningDeck = if (playerOneDeck.isNotEmpty()) playerOneDeck else playerTwoDeck

        val winningDeckScore = winningDeck.toList().reversed().mapIndexed { index, cardValue -> (index + 1) * cardValue }.sum()

        print("Winning Deck Score After $turns Turns: $winningDeckScore")
        print("\nDONE :D")
    }

    fun solution2(input: List<String>) {
        val playerOneDeck = LinkedList<Int>()
        val playerTwoDeck = LinkedList<Int>()
        var currentDeck = 1

        input.forEach {
            when {
                it.contains("Player") -> return@forEach
                it.isBlank() -> {
                    currentDeck = 2
                    return@forEach
                }
                else -> {
                    if (currentDeck == 1) {
                        playerOneDeck.addLast(it.toInt())
                    } else {
                        playerTwoDeck.addLast(it.toInt())
                    }
                }
            }
        }

        playRecursiveGame(playerOneDeck, playerTwoDeck)

        val winningDeck = if (playerTwoDeck.isEmpty()) playerOneDeck else playerTwoDeck

        val winningDeckScore = winningDeck.toList().reversed().mapIndexed { index, cardValue -> (index + 1) * cardValue }.sum()

        print("Winning Deck Score: $winningDeckScore")
        print("\nDONE :D")
    }

    private fun playRecursiveGame(playerOneDeck: LinkedList<Int>, playerTwoDeck: LinkedList<Int>, gameNumber: Int = 0) {
        var turns = 0
        val playerOneDeckHistoricalConfigurations = mutableListOf<List<Int>>()
        val playerTwoDeckHistoricalConfigurations = mutableListOf<List<Int>>()

        while (playerOneDeck.isNotEmpty() && playerTwoDeck.isNotEmpty()) {
            if (playerOneDeck.toList() in playerOneDeckHistoricalConfigurations
                && playerTwoDeck.toList() in playerTwoDeckHistoricalConfigurations) {
                playerTwoDeck.clear()
                print("Player 1 wins by infinite game prevention rule in game $gameNumber\n")
                break
            }

            playerOneDeckHistoricalConfigurations.add(playerOneDeck.toList())
            playerTwoDeckHistoricalConfigurations.add(playerTwoDeck.toList())

            val playerOneCard = playerOneDeck.pop()
            val playerTwoCard = playerTwoDeck.pop()

            if (playerOneDeck.size >= playerOneCard &&  playerTwoDeck.size >= playerTwoCard) {
                val playerOneSubDeck = playerOneDeck.copySublist(playerOneCard)
                val playerTwoSubDeck = playerTwoDeck.copySublist(playerTwoCard)

                playRecursiveGame(playerOneSubDeck, playerTwoSubDeck, gameNumber + 1)

                if (playerTwoSubDeck.isEmpty()) {
                    playerOneDeck.addLast(playerOneCard)
                    playerOneDeck.addLast(playerTwoCard)
                    print("Player 1 wins round $gameNumber\n")
                } else {
                    playerTwoDeck.addLast(playerTwoCard)
                    playerTwoDeck.addLast(playerOneCard)
                    print("Player 2 wins game $gameNumber\n")
                }
            } else {
                if (playerOneCard > playerTwoCard) {
                    playerOneDeck.addLast(playerOneCard)
                    playerOneDeck.addLast(playerTwoCard)
                    print("Player 1 wins game $gameNumber\n")
                } else {
                    playerTwoDeck.addLast(playerTwoCard)
                    playerTwoDeck.addLast(playerOneCard)
                    print("Player 2 wins game $gameNumber\n")
                }
            }

            turns += 1
            print("Game $gameNumber Turn $turns Completed\n")
        }
    }
}

fun LinkedList<Int>.copySublist(sublistElements: Int): LinkedList<Int> {
    val tmpList = LinkedList<Int>()
    this.subList(0, sublistElements).forEach { tmpList.addLast(it) }
    return tmpList
}