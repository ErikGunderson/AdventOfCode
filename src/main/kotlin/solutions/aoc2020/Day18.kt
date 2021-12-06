package solutions.aoc2020

import AoC2020Problem
import java.util.*

class Day18 : AoC2020Problem() {
    override fun solution1(input: List<String>) {
        val equationOutputs = mutableListOf<Long>()
        val operators = listOf(
            '*' to 1,
            '+' to 1
        ).toMap()

        inputFile.readLines().forEach {
            //region my attempt
//            val openParenIndices = mutableListOf(0)
//            val subEquations = mutableListOf<String>()

//            equation.forEachIndexed { index, char ->
//                when {
//                    char == '(' -> {
//                        if(equation[index - 1] != '(') subEquations.add(equation.substring(openParenIndices.last(), index))
//                        openParenIndices.add(index)
//                    }
//                    char == ')' -> {
//                        if(equation[index - 1] != ')') subEquations.add(equation.substring(openParenIndices.last(), index))
//                    }
//                    index == equation.lastIndex -> {
//                        subEquations.add(equation.substring(openParenIndices.last(), index))
//                    }
//                }
//            }
            //endregion

            equationOutputs.add(evaluateEquation(generateRpnString(it.filter { it != ' ' }, operators)))
        }

        print("Equation output sum: ${equationOutputs.sum()}")
        print("\nDONE :D")
    }

    override fun solution2(input: List<String>) {
        val equationOutputs = mutableListOf<Long>()
        val operators = listOf(
            '*' to 1,
            '+' to 2
        ).toMap()

        inputFile.readLines().forEach {
            equationOutputs.add(evaluateEquation(generateRpnString(it.filter { it != ' ' }, operators)))
        }

        print("Equation output sum: ${equationOutputs.sum()}")
        print("\nDONE :D")
    }

    //region my attempt
//    private fun evaluateEquation(equation: String) : Long {
//        var currentValue: Long = -1
//        var currentOperation = None
//
//        equation.filter { it != ' ' }.forEach {
//            when (it) {
//                '*' -> {
//                    currentOperation = Multiply
//                }
//                '+' -> {
//                    currentOperation = Add
//                }
//                else -> {
//                    if (currentValue == -1L) {
//                        currentValue = it.toLong()
//                    } else {
//                        if (currentOperation == Multiply) {
//                            currentValue *= it.toLong()
//                        } else {
//                            currentValue += it.toLong()
//                        }
//                    }
//                }
//            }
//        }
//
//        return currentValue
//    }
    //endregion

    /**
     * https://en.wikipedia.org/wiki/Shunting-yard_algorithm
     */
    private fun generateRpnString(expression: String, operators: Map<Char, Int>): String {
        val outputQueue = LinkedList<Char>()
        val operatorStack = Stack<Char>()

        expression.forEach {
            when {
                it.isDigit() -> outputQueue.addLast(it)
                it in operators.keys -> {
                    while (operatorStack.isNotEmpty()
                        && operatorStack.peek() != '('
                        && operators[operatorStack.peek()]!! >= operators[it]!!
                    ) {
                        outputQueue.addLast(operatorStack.pop())
                    }

                    operatorStack.push(it)
                }
                it == '(' -> operatorStack.push(it)
                it == ')' -> {
                    while (operatorStack.peek() != '(') {
                        outputQueue.addLast(operatorStack.pop())
                    }

                    if (operatorStack.peek() == '(') {
                        operatorStack.pop()
                    }
                }
            }
        }

        while (operatorStack.isNotEmpty()) {
            outputQueue.addLast(operatorStack.pop())
        }

        return outputQueue.joinToString("")
    }

    /**
     * https://stevenpcurtis.medium.com/evaluate-reverse-polish-notation-using-a-stack-7c618c9f80c0
     */
    private fun evaluateEquation(rpn: String): Long {
        val operandStack = Stack<Long>()

        rpn.forEach {
            if (it.isDigit()) {
                operandStack.push(it.toString().toLong())
            } else {
                val operand1 = operandStack.pop()
                val operand2 = operandStack.pop()

                operandStack.push(if (it == '*') operand1 * operand2 else operand1 + operand2)
            }
        }

        return operandStack.pop()
    }
}