package solutions.aoc2023

import solutions.utils.AoCProblem
import solutions.utils.addValue

fun main() {
    Day7().runSolution(2)
}

class Day7 : AoCProblem() {
    override val testInput: List<String>
        get() = listOf(
            "32T3K 765",
            "T55J5 684",
            "KK677 28",
            "KTJJT 220",
            "QQQJA 483"
        )

    fun solution1(input: List<String>) {
        val map = sortedMapOf<HandStrength, MutableList<Hand>>()

        //Parse
        input.forEach { hand ->
            hand.split(" ").let { (cards, bet) ->
                Hand(cards.map { it.toCardInt(solutionNumber = 1) }, bet.toInt()).let { hand ->
                    when (hand.cards.toMutableSet().size) {
                        5 -> map.addValue(HandStrength.HIGH_CARD, hand)
                        4 -> map.addValue(HandStrength.PAIR, hand)
                        3 -> {
                            if (hand.cards.groupBy { it }.values.any { it.size == 3 }) {
                                map.addValue(HandStrength.THREE_OF_A_KIND, hand)
                            } else {
                                map.addValue(HandStrength.TWO_PAIR, hand)
                            }
                        }
                        2 -> {
                            if (hand.cards.count { it == hand.cards.first() }.let { it == 1 || it == 4 }) {
                                map.addValue(HandStrength.FOUR_OF_A_KIND, hand)
                            } else {
                                map.addValue(HandStrength.FULL_HOUSE, hand)
                            }
                        }
                        1 -> map.addValue(HandStrength.FIVE_OF_A_KIND, hand)
                    }
                }
            }
        }

        //Solve
        var rank = 1
        var totalWinnings = 0L

        map.forEach { (_, hands) ->
            hands.sorted().forEach {
                (rank * it.bid).let { handWinnings ->
                    println("$rank * ${it.bid} = $handWinnings")
                    totalWinnings += handWinnings
                    rank++
                }
            }
        }

        println("Total winnings $totalWinnings")
    }

    fun solution2(input: List<String>) {
        val map = sortedMapOf<HandStrength, MutableList<Hand>>()

        //Parse
        input.forEach { hand ->
            hand.split(" ").let { (cards, bet) ->
                Hand(cards.map { it.toCardInt(solutionNumber = 2) }, bet.toInt()).let { hand ->
                    hand.cards.filterNot { it == 1 }.let { filteredHand ->
                        val jokerCount = 5 - filteredHand.size

                        when (filteredHand.toMutableSet().size) {
                            5 -> map.addValue(HandStrength.HIGH_CARD, hand)                 //AKQT9
                            4 -> map.addValue(HandStrength.PAIR, hand)                      //JKQA9, KKQA9
                            3 -> when(jokerCount) {
                                2, 1 -> map.addValue(HandStrength.THREE_OF_A_KIND, hand)    //JJKQA, JKKQA
                                else -> if (filteredHand.groupBy { it }.values.any { it.size == 3 }) {
                                    map.addValue(HandStrength.THREE_OF_A_KIND, hand)        //KKKQA
                                } else {
                                    map.addValue(HandStrength.TWO_PAIR, hand)               //KKQQA
                                }
                            }
                            2 -> when(jokerCount) {
                                3 -> map.addValue(HandStrength.FOUR_OF_A_KIND, hand)        //JJJKQ
                                2 -> map.addValue(HandStrength.FOUR_OF_A_KIND, hand)        //JJKKA
                                1 -> if (filteredHand.count { it == filteredHand.first() } in listOf(1, 3)) {
                                    map.addValue(HandStrength.FOUR_OF_A_KIND, hand)         //JKKKQ
                                } else {
                                    map.addValue(HandStrength.FULL_HOUSE, hand)             //JKKQQ
                                }
                                else -> if (filteredHand.count { it == filteredHand.first() } in listOf(1, 4)) {
                                        map.addValue(HandStrength.FOUR_OF_A_KIND, hand)     //KKKKQ
                                    } else {
                                        map.addValue(HandStrength.FULL_HOUSE, hand)         //KKQQQ
                                    }
                            }
                            1 -> map.addValue(HandStrength.FIVE_OF_A_KIND, hand)            //JJJJK, KKKKK
                            0 -> map.addValue(HandStrength.FIVE_OF_A_KIND, hand)            //JJJJJ
                        }
                    }
                }
            }
        }

        //Solve
        var rank = 1
        var totalWinnings = 0L

        map.forEach { (_, hands) ->
            hands.sorted().forEach {
                (rank * it.bid).let { handWinnings ->
                    println("$rank * ${it.bid} = $handWinnings")
                    totalWinnings += handWinnings
                    rank++
                }
            }
        }

        println("Total winnings $totalWinnings")
    }
}

enum class HandStrength {
    HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND
}

private data class Hand(
    val cards: List<Int>,
    val bid: Int
) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        this.cards.forEachIndexed { index, card ->
            card.compareTo(other.cards[index]).let { if (it != 0) return it }
        }

        return 0
    }
}

private fun Char.toCardInt(solutionNumber: Int): Int = when (this) {
    'T' -> 10
    'J' -> if (solutionNumber == 1) 11 else 1
    'Q' -> 12
    'K' -> 13
    'A' -> 14
    else -> digitToInt()
}