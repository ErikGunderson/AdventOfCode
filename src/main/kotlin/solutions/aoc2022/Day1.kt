package solutions.aoc2022

import solutions.utils.AoCProblem

fun main() {
    Day1().runAllSolutions()
}

class Day1: AoCProblem() {
    fun solution1(input: List<String>) {
        var mapIndex = 0
        val calorieMap = mutableMapOf<Int, MutableList<Int>>()

        input.forEach {
            if (it.isNotBlank()) {
                if (!calorieMap.contains(mapIndex)) calorieMap[mapIndex] = mutableListOf()
                calorieMap[mapIndex]!!.add(it.toInt())
            } else {
                mapIndex += 1
            }
        }

        print("Maximum calories packed :: ${calorieMap.maxOf { it.value.sum() }}")
    }

    fun solution2(input: List<String>) {
        var mapIndex = 0
        val calorieMap = mutableMapOf<Int, MutableList<Int>>()

        input.forEach {
            if (it.isNotBlank()) {
                if (!calorieMap.contains(mapIndex)) calorieMap[mapIndex] = mutableListOf()
                calorieMap[mapIndex]!!.add(it.toInt())
            } else {
                mapIndex += 1
            }
        }

        print("calories packed among top 3 :: ${calorieMap.values.map { it.sum() }.sortedDescending().take(3).sum()}")
    }
}