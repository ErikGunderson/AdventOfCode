package solutions.aoc2020

import AoC2020Problem

class Day2 : AoC2020Problem() {
    override fun solution1(input: List<String>) {
        var validPasswordCount = 0

        input.forEach { singleInput ->
            singleInput.split(":", limit = 2).let {
                val policy = it[0].split(" ").let {
                    it[0] to it[1].first()
                }

                val policyRange = policy.first.split("-").map { it.toInt() }
                val password = it[1].trim()

                val importantCharCount = password.count { it == policy.second }
                if (importantCharCount >= policyRange[0] && importantCharCount <= policyRange[1]) validPasswordCount += 1
            }
        }

        print("Valid password count is $validPasswordCount")
        print("\nDONE :D")
    }

    override fun solution2(input: List<String>) {
        var validPasswordCount = 0

        input.forEach { singleInput ->
            singleInput.split(":", limit = 2).let {
                val policy = it[0].split(" ").let {
                    it[0] to it[1].first()
                }

                val policyIndices = policy.first.split("-").map { it.toInt() - 1 }
                val password = it[1].trim()

                val isImportantCharAtFirstIndex = password[policyIndices[0]] == policy.second
                val isImportantCharAtSecondIndex = password[policyIndices[1]] == policy.second

                if (isImportantCharAtFirstIndex xor isImportantCharAtSecondIndex) validPasswordCount += 1
            }
        }

        print("Valid password count is $validPasswordCount")
        print("\nDONE :D")
    }
}