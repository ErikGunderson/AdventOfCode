package solutions.utils

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun List<Long>.gcd(): Long {
    var result = this[0]
    for (i in 1 until this.size) result = gcd(result, this[i])
    return result
}

fun lcm(a: Long, b: Long): Long = (a * b) / gcd(a, b)

fun List<Long>.lcm(): Long {
    var result = this[0]
    for (i in 1 until this.size) result = lcm(result, this[i])
    return result
}