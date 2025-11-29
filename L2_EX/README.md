# Kotlin Basics Practice

This project contains a few small Kotlin programs to practice core language features such as:
- Working with lists
- Using `filter`, `map`, and `reduce`
- Calculating squares of numbers
- Generating prime and even numbers

## Features

### 1. Prime & Even Numbers

A console program that:

- Finds the **first 50 prime numbers** and stores them in a list.
- Prints the list of primes.
- Creates a list of the **first 50 even numbers**.
- Combines the prime and even lists into a single list.
- Prints the combined list.

_Key functions (example):_

- `getFirstNPrimes(n: Int): List<Int>` – generates the first `n` primes  
- `isPrime(num: Int): Boolean` – checks if a number is prime  

### 2. List Operations with `filter`, `map`, and `reduce`

Another console example that:

- Creates a list of integers (e.g. `1..100`).
- Uses:
  - `filter { ... }` to get all **even numbers**
  - `map { ... }` to get the **square** of each number
  - `reduce { acc, n -> ... }` to compute the **sum** of all numbers
- Prints:
  - `Evens:`
  - `Squares:`
  - `Sum:`

### 3. Square of a Number

A simple helper to compute the square of an integer, for example:

```kotlin
fun square(n: Int): Int = n * n
