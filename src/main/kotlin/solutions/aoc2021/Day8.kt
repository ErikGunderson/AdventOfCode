package solutions.aoc2021

import solutions.utils.AoCProblem

class Day8 : AoCProblem() {

    fun solution1(input: List<String>) {
        val uniqueDigitCount = input.sumOf {
            it.split(" | ")[1].split(" ").count { it.length.isUniqueSevenSegmentDigit() }
        }

        print("Total unique digit appearances: $uniqueDigitCount")
    }

    fun solution2(input: List<String>) {
        //the digit 0 is 8 without one piece from 4
        //have digit 1, unique
        //the digit 2 is 8 without 2 pieces from 4
        //the digit 3 is 8 missing two pieces - ELSE case
        //the digit 4 is unique
        //the digit 5 is 8 without 2 pieces, one of which is in 1
        //the digit 6 is 8 missing 1 piece from 1
        //the digit 7 is unique
        //the digit 8 is unique
        //the digit 9 is 8 missing on piece - ELSE case

        val sumOfDisplayDigits = input.sumOf { singleDisplay ->
            val digits = Array(10) { setOf<Char>() }

            singleDisplay.split(" | ").let { wireSegments ->
                //Solve for digits
                wireSegments[0].split(" ").groupBy { it.length.isUniqueSevenSegmentDigit() }
                    .let { groupedDigits ->
                        groupedDigits[true]!!.forEach { segment ->
                            when (segment.length) {
                                2 -> digits[1] = segment.toSortedSet()
                                3 -> digits[7] = segment.toSortedSet()
                                4 -> digits[4] = segment.toSortedSet()
                                7 -> digits[8] = segment.toSortedSet()
                            }
                        }

                        groupedDigits[false]!!.sortedBy { it.length }.forEach { segment ->
                            when (segment.length) {
                                5 -> {
                                    //2, 3, 5
                                    val subset = digits[8].minus(segment.toSet())
                                    when {
                                        subset.intersect(digits[4]).size == 2 -> digits[2] = segment.toSortedSet()
                                        subset.intersect(digits[1]).size == 1 -> digits[5] = segment.toSortedSet()
                                        else -> digits[3] = segment.toSortedSet()
                                    }
                                }
                                6 -> {
                                    //0, 6, 9
                                    val subset = digits[8].minus(segment.toSet())
                                    when {
                                        subset.intersect(digits[5]).size == 1 -> digits[0] = segment.toSortedSet()
                                        subset.intersect(digits[1]).size == 1 -> digits[6] = segment.toSortedSet()
                                        else -> digits[9] = segment.toSortedSet()
                                    }
                                }
                            }
                        }
                    }

                //determine digits in output and sum them
                wireSegments[1].split(" ")
                    .map { output -> digits.indexOfFirst { it == output.toSortedSet() } }
                    .reversed()
                    .reduceIndexed { index, acc, value -> acc + (value * Math.pow(10.0, index.toDouble())).toInt() }
            }
        }

        print("Sum of all displays: $sumOfDisplayDigits")
    }
}

fun Int.isUniqueSevenSegmentDigit() = this in 2..4 || this == 7