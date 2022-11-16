package solutions.aoc2021

import solutions.utils.AoCProblem

class Day6 : AoCProblem() {
    fun solution1(input: List<String>) {
        val groupedFishByDay = Array(9) { index -> FishGroup(index, 0) }

        input.forEach {
            it.split(",").map { it.toInt() }.groupBy { it }.entries.forEach { (day, fish) ->
                groupedFishByDay[day].fishCount += fish.size
            }
        }

        for (simulatedDay in 1..80) {
            var fishMakingNewFish = 0L
            groupedFishByDay.forEachIndexed { index, group ->
                if (index == 0) fishMakingNewFish = group.fishCount
                else groupedFishByDay[index - 1].fishCount = group.fishCount
            }

            groupedFishByDay[6].fishCount += fishMakingNewFish
            groupedFishByDay[8].fishCount = fishMakingNewFish
        }

        print("Total fish after 80 days: ${groupedFishByDay.countFish()}")
    }

    fun solution2(input: List<String>) {
        val groupedFishByDay = Array(9) { index -> FishGroup(index, 0) }

        input.forEach {
            it.split(",").map { it.toInt() }.groupBy { it }.entries.forEach { (day, fish) ->
                groupedFishByDay[day].fishCount += fish.size
            }
        }

        for (simulatedDay in 1..256) {
            var fishMakingNewFish = 0L
            groupedFishByDay.forEachIndexed { index, group ->
                if (index == 0) fishMakingNewFish = group.fishCount
                else groupedFishByDay[index - 1].fishCount = group.fishCount
            }

            groupedFishByDay[6].fishCount += fishMakingNewFish
            groupedFishByDay[8].fishCount = fishMakingNewFish
        }

        print("\nTotal fish after 256 days: ${groupedFishByDay.countFish()}")
    }
}

data class FishGroup(
    val daysLeft: Int,
    var fishCount: Long
)

fun Array<FishGroup>.countFish(): Long = this.sumOf { it.fishCount }