package solutions.aoc2020

import solutions.utils.AoCProblem
import solutions.aoc2020.ContainsGoldBag.*

class Day7: AoCProblem() {
    fun solution1(input: List<String>) {
        val bagMap = mutableMapOf<String, BagsEntry>()
        var goldContainerBagCount = 0

        input.forEach { singleInput ->
            val keyValueSplit = singleInput.split(" contain ")

            val nestedBags = mutableListOf<String>()
            keyValueSplit[1].split(", ").forEach {
                val bagAmountColorSplit = it.split(" ", limit = 2).let {
                    if (it[0] == "no") 0 to ""
                    else it[0].toInt() to it[1].trimEnd('.', 's')
                }

                for (i in 0 until bagAmountColorSplit.first) {
                    nestedBags.add(bagAmountColorSplit.second)
                }
            }

            bagMap[keyValueSplit[0].trimEnd('s')] = BagsEntry(nestedBags.distinct(), if (nestedBags.isEmpty()) No else Unknown)
        }

        bagMap.values.forEach { if (checkBagContentsForGold(bagMap, it)) goldContainerBagCount += 1 }

        print("Total count of bags that contain a gold bag is: $goldContainerBagCount")
        print("\nDONE :D")
    }

    private fun checkBagContentsForGold(bagMap: Map<String, BagsEntry>, bagsToCheck: BagsEntry): Boolean {
        return when (bagsToCheck.containsGoldBag) {
            Yes -> { return true }
            No -> { return false }
            Unknown -> {
                bagsToCheck.containedBags.forEach { nestedBagName ->
                    val nestedBagsEntry = bagMap[nestedBagName]

                    if (nestedBagName == "shiny gold bag" || nestedBagsEntry?.containsGoldBag == Yes) {
                        bagsToCheck.containsGoldBag = Yes
                        return true
                    } else {
                        if (nestedBagsEntry != null && bagsToCheck.containedBags.isNotEmpty()) {
                            if (checkBagContentsForGold(bagMap, nestedBagsEntry)) {
                                bagsToCheck.containsGoldBag = Yes
                                return true
                            }
                        } else{
                            nestedBagsEntry?.containsGoldBag = No
                        }
                    }
                }

                false
            }
        }
    }

    fun solution2(input: List<String>) {
        val bagMap = mutableMapOf<String, List<String>>()
        var bagCount = 0

        input.forEach { singleInput ->
            val keyValueSplit = singleInput.split(" contain ")

            val nestedBags = mutableListOf<String>()
            keyValueSplit[1].split(", ").forEach {
                val bagAmountColorSplit = it.split(" ", limit = 2).let {
                    if (it[0] == "no") 0 to ""
                    else it[0].toInt() to it[1].trimEnd('.', 's')
                }

                for (i in 0 until bagAmountColorSplit.first) {
                    nestedBags.add(bagAmountColorSplit.second)
                }
            }

            bagMap[keyValueSplit[0].trimEnd('s')] = nestedBags
        }

        checkForBagCountInBag(bagMap, bagMap["shiny gold bag"] ?: emptyList()) { bagCount += 1 }

        print("Total count of bags is: $bagCount")
        print("\nDONE :D")
    }

    private fun checkForBagCountInBag(bagMap: Map<String, List<String>>, bagContents: List<String>, countBag: () -> Unit) {
        bagContents.forEach { nestedBagName ->
            val nestedBagsEntry = bagMap[nestedBagName] ?: emptyList()

            countBag()
            if (nestedBagsEntry.isNotEmpty()) {
                checkForBagCountInBag(bagMap, nestedBagsEntry, countBag)
            }
        }
    }
}

sealed class ContainsGoldBag {
    object Yes: ContainsGoldBag()
    object No: ContainsGoldBag()
    object Unknown: ContainsGoldBag()
}

data class BagsEntry(
    var containedBags: List<String>,
    var containsGoldBag: ContainsGoldBag
)