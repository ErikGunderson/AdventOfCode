package solutions.aoc2023

import solutions.utils.AoCProblem

fun main() {
    Day5().runSolution(2)
}

class Day5 : AoCProblem() {

    override val testInput: List<String>
        get() = listOf(
            "seeds: 79 14 55 13",
            "",
            "seed-to-soil map:",
            "50 98 2",
            "52 50 48",
            "",
            "soil-to-fertilizer map:",
            "0 15 37",
            "37 52 2",
            "39 0 15",
            "",
            "fertilizer-to-water map:",
            "49 53 8",
            "0 11 42",
            "42 0 7",
            "57 7 4",
            "",
            "water-to-light map:",
            "88 18 7",
            "18 25 70",
            "",
            "light-to-temperature map:",
            "45 77 23",
            "81 45 19",
            "68 64 13",
            "",
            "temperature-to-humidity map:",
            "0 69 1",
            "1 0 69",
            "",
            "humidity-to-location map:",
            "60 56 37",
            "56 93 4"
        )

    fun solution1(input: List<String>) {
        val seeds = input[0].split(" ").let { splits ->
            splits.subList(1, splits.size).map { it.toLong() }
        }

        val almanacMaps = List(7) { mutableListOf<AlmanacRow>() }

        var currentAlmanacIndex = 0
        //Skip the first rows because seeds don't match the format of other categories and have already been parsed
        input.subList(2, input.size).forEach {
            when {
                it.isBlank() -> {
                    currentAlmanacIndex++
                }
                it.first().isDigit() -> {
                    it.split(" ").map { it.toLong() }.let { (destRangeStart, sourceRangeStart, rangeLength) ->
                        almanacMaps[currentAlmanacIndex].add(
                            AlmanacRow(
                                sourceRangeStart..<sourceRangeStart + rangeLength,
                                destRangeStart..<destRangeStart + rangeLength
                            )
                        )
                    }
                }
                else -> { /* NO OP */ }
            }
        }

        val nearestLocationNumber = seeds.map { seed ->
            var sourceVal = seed

            almanacMaps.forEach { almanac ->
                almanac.firstOrNull { it.sourceRange.contains(sourceVal) }?.let {
                    sourceVal = it.destinationRange.first + (sourceVal - it.sourceRange.first)
                }
            }

            sourceVal
        }.min()

        print("Nearest location $nearestLocationNumber")
    }

    //TODO optimize
    fun solution2(input: List<String>) {
        val seeds = input[0].split(" ").let { splits ->
            splits.subList(1, splits.size).map { it.toLong() }.windowed(size = 2, step = 2).map { (rangeStart, rangeLength) ->
                rangeStart..<(rangeStart + rangeLength)
            }
        }

        val almanacMaps = List(7) { mutableListOf<AlmanacRow>() }

        var currentAlmanacIndex = 0
        //Skip the first rows because seeds don't match the format of other categories and have already been parsed
        input.subList(2, input.size).forEach {
            when {
                it.isBlank() -> {
                    currentAlmanacIndex++
                }
                it.first().isDigit() -> {
                    it.split(" ").map { it.toLong() }.let { (destRangeStart, sourceRangeStart, rangeLength) ->
                        almanacMaps[currentAlmanacIndex].add(
                            AlmanacRow(
                                sourceRangeStart..<sourceRangeStart + rangeLength,
                                destRangeStart..<destRangeStart + rangeLength
                            )
                        )
                    }
                }
                else -> { /* NO OP */ }
            }
        }

        val nearestLocationNumber = seeds.map { seedRange ->
            seedRange.minOfOrNull { seed ->
                var sourceVal = seed

                almanacMaps.forEach { almanac ->
                    almanac.firstOrNull { it.sourceRange.contains(sourceVal) }?.let {
                        sourceVal = it.destinationRange.first + (sourceVal - it.sourceRange.first)
                    }
                }

                sourceVal
            }!!
        }.onEach { print("location value $it\n") }.min()

        print("Nearest location $nearestLocationNumber")
    }
}

private data class AlmanacRow(
    val sourceRange: LongRange,
    val destinationRange: LongRange
)