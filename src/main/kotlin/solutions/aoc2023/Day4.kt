package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day4().runSolution(2)
}

class Day4: AoCProblem() {

    override val testInput: List<String>
        get() = listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
        )

    fun solution1(input: List<String>) {
        var cardPointSum = 0L

        input.forEach {
            it.split(':', '|').let { (cardNumber, winningNumberString, ourNumberString) ->
                var cardPoints = 0
                val winningNumbers = winningNumberString.windowed(size = 3, step = 3).map { it.trim().toInt() }

                ourNumberString.windowed(size = 3, step = 3).forEach {
                    if (winningNumbers.contains(it.trim().toInt())) {
                        if (cardPoints == 0) cardPoints = 1 else cardPoints *= 2
                    }
                }

                cardPointSum += cardPoints
            }
        }

        print("Card points sum is $cardPointSum")
    }

    fun solution2(input: List<String>) {
        val scratchCards = List(input.size) { ScratchCard() }

        input.forEachIndexed { cardNumber, cardString ->
            cardString.split(':', '|').let { (_, winningNumberString, ourNumberString) ->
                var winningNumberCount = 0
                val winningNumbers = winningNumberString.windowed(size = 3, step = 3).map { it.trim().toInt() }

                ourNumberString.windowed(size = 3, step = 3).forEach {
                    if (winningNumbers.contains(it.trim().toInt())) winningNumberCount++
                }

                if (winningNumberCount > 0) {
                    scratchCards.subList(
                        (cardNumber + 1).coerceAtMost(scratchCards.size),
                        (cardNumber + winningNumberCount + 1).coerceAtMost(scratchCards.size)
                    ).forEach {
                        it.incrementCardCount(scratchCards[cardNumber].getCardCount())
                    }
                }
            }
        }

        print("Total number of scratch cards: ${scratchCards.sumOf { it.getCardCount() }}")
    }
}

private data class ScratchCard(private var copiesOfCard: Int = 1) {
    fun incrementCardCount(timesToIncrement: Int) {
        copiesOfCard += timesToIncrement
    }

    fun getCardCount() = copiesOfCard
}