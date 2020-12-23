package solutions

import FileReader

class Day19 : FileReader() {
    fun solution1() {
        var parsingStrategy = 1
        val rules = mutableMapOf<Int, String>()
        val inputs = mutableListOf<String>()

        inputFile.readLines().forEach {
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

    fun solution2() {
        var parsingStrategy = 1
        val rules = mutableMapOf<Int, String>()
        val inputs = mutableListOf<String>()

        inputFile.readLines().forEach {
            if (it == "") run { parsingStrategy = 2; return@forEach }

            if (parsingStrategy == 1) {
                it.split(": ").let { (ruleNumber, rule) ->
                    rules[ruleNumber.toInt()] = when(ruleNumber) {
                        "8" -> {
                            rule.split('|')[0]
                                .trim()
                                .split(' ')
                                .map { "$it{countOne}" }
                                .joinToString(" ")
                        }
                        "11" -> {
                            rule.split('|')[0]
                                .trim()
                                .split(' ')
                                .map { "$it{countTwo}" }
                                .joinToString(" ")
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
                inputs.forEachIndexed { index, input ->
                    val regexString = "^${masterRule.filter { it != ' ' }}$"
                        .replace("countOne", countOne.toString())
                        .replace("countTwo", countTwo.toString())

                    if (Regex(regexString).matches(input)) matchingInputIndices.add(index)
                }
            }
        }

        print("Matching Inputs: ${matchingInputIndices.distinct().size}")
        print("\nDONE :D")
    }
}