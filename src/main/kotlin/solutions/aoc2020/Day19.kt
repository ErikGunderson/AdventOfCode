package solutions.aoc2020

import solutions.utils.AoCProblem

class Day19 : AoCProblem() {
    /**
     * NOTE: use these rules for part 1:
     *
     * 8: 42
     * 11: 42 31
     */
    fun solution1(input: List<String>) {
        var parsingStrategy = 1
        val rules = mutableMapOf<Int, String>()
        val inputs = mutableListOf<String>()

        input.forEach {
            if (it == "") run { parsingStrategy = 2; return@forEach }

            if (parsingStrategy == 1) {
                it.split(": ").let { (ruleNumber, rule) ->
                    rules[ruleNumber.toInt()] = rule.trim('"')
                }
            } else {
                inputs.add(it)
            }
        }

        var masterRule = rules[0]!!

        while (masterRule.any { it.isDigit() }) {
            Regex("[0-9]+").find(masterRule)?.let {
                masterRule = masterRule.replaceFirst(it.value, "(${rules[it.value.toInt()]})")
            }
        }

        val matchingInputs = inputs.count { Regex("^${ masterRule.filter { it != ' ' } }$").matches(it) }

        print("Matching Inputs: $matchingInputs")
        print("\nDONE :D")
    }

    /**
     * NOTE: use these rules for part 2:
     *
     * 8: 42 | 42 8
     * 11: 42 31 | 42 11 31
     */
    fun solution2(input: List<String>) {
        var parsingStrategy = 1
        val rules = mutableMapOf<Int, String>()
        val inputs = mutableListOf<String>()

        input.forEach {
            if (it == "") run { parsingStrategy = 2; return@forEach }

            if (parsingStrategy == 1) {
                it.split(": ").let { (ruleNumber, rule) ->
                    rules[ruleNumber.toInt()] = when(ruleNumber) {
                        "8" -> {
                            rule.split('|')[0]
                                .trim()
                                .split(' ')
                                .joinToString(" ") { "$it{countOne}" }
                        }
                        "11" -> {
                            rule.split('|')[0]
                                .trim()
                                .split(' ')
                                .joinToString(" ") { "$it{countTwo}" }
                        }
                        else -> rule.trim('"')
                    }
                }
            } else {
                inputs.add(it)
            }
        }

        var masterRule = rules[0]!!

        while (masterRule.any { it.isDigit() }) {
            Regex("[0-9]+").find(masterRule)?.let {
                masterRule = masterRule.replaceFirst(it.value, "(${rules[it.value.toInt()]})")
            }
        }

        val matchingInputIndices = mutableListOf<Int>()

        for (countOne in 1..5) {
            for (countTwo in 1..5) {
                inputs.forEachIndexed { index, singleInput ->
                    val regexString = "^${masterRule.filter { it != ' ' }}$"
                        .replace("countOne", countOne.toString())
                        .replace("countTwo", countTwo.toString())

                    if (Regex(regexString).matches(singleInput)) matchingInputIndices.add(index)
                }
            }
        }

        print("Matching Inputs: ${matchingInputIndices.distinct().size}")
        print("\nDONE :D")
    }
}