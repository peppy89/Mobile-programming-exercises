fun main() {
    // 1. First 50 prime numbers
    val primeList = getFirstNPrimes(50)
    println("First 50 primes:")
    println(primeList)

    // 2. First 50 even numbers
    val evenList = (1..50).map { it * 2 }
    println("\nFirst 50 evens:")
    println(evenList)

    // 3. Combine them
    val combinedList = primeList + evenList
    println("\nCombined list (primes + evens):")
    println(combinedList)
}

fun getFirstNPrimes(n: Int): List<Int> {
    val primes = mutableListOf<Int>()
    var num = 2
    while (primes.size < n) {
        if (isPrime(num)) primes.add(num)
        num++
    }
    return primes
}

fun isPrime(num: Int): Boolean {
    if (num < 2) return false
    val limit = kotlin.math.sqrt(num.toDouble()).toInt()
    for (i in 2..limit) {
        if (num % i == 0) return false
    }
    return true
}

