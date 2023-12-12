package solutions.aoc2023

import solutions.utils.AoCProblem
import solutions.utils.lcm

fun main() {
    Day8().runSolution(2)
}

class Day8: AoCProblem() {

    override val testInput: List<String>
        get() = listOf(
            "LR",
            "",
            "11A = (11B, XXX)",
            "11B = (XXX, 11Z)",
            "11Z = (11B, XXX)",
            "22A = (22B, XXX)",
            "22B = (22C, 22C)",
            "22C = (22Z, 22Z)",
            "22Z = (22B, 22B)",
            "XXX = (XXX, XXX)"
        )

    fun solution1(input: List<String>) {
        val nodeMap = HashMap<String, NetworkNode>(input.size - 2)
        val traversalInstructions = input[0]

        val nodePattern = Regex("(?<node>[1-Z]+)\\W*(?<left>[1-Z]+)\\W*(?<right>[1-Z]+)")
        input.subList(2, input.size).forEach { node ->
            nodePattern.find(node)!!.groups.let { match ->
                val currentNodeValue = match["node"]!!.value

                nodeMap[currentNodeValue] = NetworkNode(
                    currentNodeValue,
                    match["left"]!!.value,
                    match["right"]!!.value,
                )
            }
        }

        var currentNode = nodeMap["AAA"]!!
        var stepCount = 0
        while (currentNode.nodeValue != "ZZZ") {
            currentNode = if (traversalInstructions[stepCount % traversalInstructions.length] == 'L') {
                nodeMap[currentNode.left]!!
            } else {
                nodeMap[currentNode.right]!!
            }

            stepCount++
        }

        println("Number of steps to reach ZZZ: $stepCount steps")
    }

    fun solution2(input: List<String>) {
        val nodeMap = HashMap<String, NetworkNode>(input.size - 2)
        val traversalInstructions = input[0]

        val nodePattern = Regex("(?<node>[1-Z]+)\\W*(?<left>[1-Z]+)\\W*(?<right>[1-Z]+)")
        input.subList(2, input.size).forEach { node ->
            nodePattern.find(node)!!.groups.let { match ->
                val currentNodeValue = match["node"]!!.value

                nodeMap[currentNodeValue] = NetworkNode(
                    currentNodeValue,
                    match["left"]!!.value,
                    match["right"]!!.value,
                )
            }
        }

        val stepsToTraverse = nodeMap.filterKeys { nodeValue -> nodeValue.endsWith('A') }.values.map { startingNode ->
            var stepCount = 0L
            var currentNode = startingNode

            do {
                currentNode = if (traversalInstructions[(stepCount % traversalInstructions.length).toInt()] == 'L') {
                    nodeMap[currentNode.left]!!
                } else {
                    nodeMap[currentNode.right]!!
                }

                stepCount++
            } while (!currentNode.nodeValue.endsWith('Z'))

            stepCount
        }

        val totalSteps = stepsToTraverse.lcm()

        println("Number of steps for all starter nodes to end in Z: $totalSteps steps")
    }
}



private data class NetworkNode(
    val nodeValue: String,
    val left: String,
    val right: String
)