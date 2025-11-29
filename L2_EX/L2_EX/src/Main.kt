//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val numbers = (1..100).toList()
    val evens = numbers.filter{it % 2 == 0}
    val squares = numbers.map{it * it}
    val sum = numbers.reduce{ acc, n -> acc + n}
    // Print results
    println("Evens:")
    println(evens)

    println("\nSquares:")
    println(squares)

    println("\nSum:")
    println(sum)
}