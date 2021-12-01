package solutions.aoc2020

import AoC2020Problem

class Day16 : AoC2020Problem() {
    fun solution1() {
        var parsingStrategy = 1

        val fieldRangeMap = mutableMapOf<String, Pair<IntRange, IntRange>>()
        val myTicket = mutableListOf<Int>()
        val otherTickets = mutableListOf<List<Int>>()

        val invalidFields = mutableListOf<Int>()

        inputFile.readLines().forEach parsing@{
            if (it.isBlank()) run { parsingStrategy += 1; return@parsing }

            when(parsingStrategy) {
                1 -> {
                    val firstSplit = it.split(": ")
                    fieldRangeMap[firstSplit[0]] = firstSplit[1].let {
                        val ranges = it.split(" or ")
                        val firstRange = ranges[0].split("-").map { it.toInt() }.let { it[0] to it[1] }.toIntRange()
                        val secondRange = ranges[1].split("-").map { it.toInt() }.let { it[0] to it[1] }.toIntRange()

                        firstRange to secondRange
                    }
                }
                2 -> {
                    if (!it.contains(",")) return@parsing

                    myTicket.addAll(it.split(",").map { it.toInt() })
                }
                3 -> {
                    if (!it.contains(",")) return@parsing

                    otherTickets.add(it.split(",").map { it.toInt() })
                }
            }
        }

        otherTickets.flatten().forEach checkValidity@ { field ->
            fieldRangeMap.values.forEach { (firstRange, secondRange) ->
                if (field in firstRange || field in secondRange) {
                    return@checkValidity
                }
            }

            invalidFields.add(field)
        }

        print("ticket scanning error rate: ${invalidFields.sum()}")
        print("\nDONE :D")
    }

    fun solution2() {
        var parsingStrategy = 1

        val fieldRangeMap = mutableMapOf<String, Pair<IntRange, IntRange>>()
        val myTicket = mutableListOf<Int>()
        val otherTickets = mutableListOf<List<Int>>()

        val invalidTicketIndices = mutableListOf<Int>()
        val fieldTicketIndices = mutableListOf<MutableList<Field>>()

        inputFile.readLines().forEach parsing@{
            if (it.isBlank()) run { parsingStrategy += 1; return@parsing }

            when(parsingStrategy) {
                1 -> {
                    val firstSplit = it.split(": ")
                    fieldRangeMap[firstSplit[0]] = firstSplit[1].let {
                        val ranges = it.split(" or ")
                        val firstRange = ranges[0].split("-").map { it.toInt() }.let { it[0] to it[1] }.toIntRange()
                        val secondRange = ranges[1].split("-").map { it.toInt() }.let { it[0] to it[1] }.toIntRange()

                        firstRange to secondRange
                    }
                }
                2 -> {
                    if (!it.contains(",")) return@parsing

                    myTicket.addAll(it.split(",").map { it.toInt() })
                }
                3 -> {
                    if (!it.contains(",")) return@parsing

                    otherTickets.add(it.split(",").map { it.toInt() })
                }
            }
        }

        otherTickets.forEachIndexed checkValidity@ { index, ticket ->
            ticket.forEach checkFields@ { field ->
                fieldRangeMap.values.forEach { (firstRange, secondRange) ->
                    if (field in firstRange || field in secondRange) {
                        return@checkFields
                    }
                }

                invalidTicketIndices.add(index)
            }
        }

        invalidTicketIndices.sortedByDescending { it }.forEach { otherTickets.removeAt(it) }

        for (index in 0 until myTicket.size) {
            fieldTicketIndices.add(fieldRangeMap.keys.map { Field(it) }.toMutableList())
        }

        otherTickets.forEach checkFieldsOnTicket@ { ticket ->
            ticket.forEachIndexed { index, field ->
                fieldRangeMap.forEach { (fieldName, ranges) ->
                    if (field !in ranges.first && field !in ranges.second) {
                        fieldTicketIndices[index].removeAll { it.fieldName == fieldName }
                    }
                }
            }
        }

        while (fieldTicketIndices.any { it.size > 1 }) {
            val fieldToAssign = fieldTicketIndices.first { it.size == 1 && !it[0].assigned }.let { it[0] }
            fieldToAssign.assigned = true

            fieldTicketIndices.forEach removeAssigned@ {
                if (it[0] !== fieldToAssign) {
                    it.removeAll { it.fieldName == fieldToAssign.fieldName }
                }
            }
        }

        var multipliedValue = 1L
        fieldTicketIndices.forEachIndexed { index, fields ->
            if (fields[0].fieldName.contains("departure")) {
                multipliedValue *= myTicket[index]
            }
        }

        print("Multiplied departure value: $multipliedValue")
        print("\nDONE :D")
    }
}

fun Pair<Int, Int>.toIntRange() : IntRange {
    return first..second
}

data class Field(
    var fieldName: String,
    var assigned: Boolean = false
)