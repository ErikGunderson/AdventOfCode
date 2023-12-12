package solutions.utils

/**
 * https://www.geeksforgeeks.org/chinese-remainder-theorem-set-1-introduction/
 * https://www.geeksforgeeks.org/implementation-of-chinese-remainder-theorem-inverse-modulo-based-implementation/?ref=lbp
 */
fun solveCRT(input: List<CrtObject>): Long {
    var formulaSum = 0L
    val inputProduct = input.productOfValues()

    input.forEach {
        val pp = inputProduct / it.value
        formulaSum += (it.remainder * pp * modInverse(pp, it.value)!!)
    }

    return formulaSum % inputProduct
}

private fun modInverse(a: Long, m: Long) : Long? {
    val params = GcdParams(0, 0)
    val gcd = findGcdExtended(a, m, params)

    return if (gcd != 1L) {
        print("\nError - Inverse does not exist!")
        null
    } else {
        return (params.x % m + m) % m
    }
}

private fun findGcdExtended(a: Long, b:Long, params: GcdParams) : Long {
    if (a == 0L) {
        params.x = 0
        params.y = 1
        return b
    }

    val recursiveParams = GcdParams(0, 0)
    val gcd = findGcdExtended(b % a, a, recursiveParams)

    params.x = recursiveParams.y - (b / a) * recursiveParams.x
    params.y = recursiveParams.x

    return gcd
}

//Longs, assume big-ass numbers
interface CrtObject {
    val value: Long
    val remainder: Long
}

fun List<CrtObject>.productOfValues() = this.fold(1L) { acc, crtObject -> acc * crtObject.value }

data class GcdParams(
    var x: Long,
    var y: Long
)
