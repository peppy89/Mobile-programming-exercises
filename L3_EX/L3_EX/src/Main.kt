//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
open class Employee(
    protected val name: String,
    protected val baseSalary: Double,
){
    open fun calculateSalary(): Double = baseSalary
    fun getNames(): String = name
}

class FullTimeEmployee(
    name: String,
    baseSalary: Double,
    val bonus: Double): Employee (name,baseSalary) {
        override fun calculateSalary(): Double{
            return baseSalary + bonus
        }
}

class PartTimeEmployee(
    name: String,
    private val hourlyRate: Double,
    private val hoursWorked: Double,
): Employee(name, 0.0){
    override fun calculateSalary(): Double{
        return hourlyRate * hoursWorked
    }
}

fun main() {
    // mapOf to store multiple employees
    val employees: Map<String, Employee> = mapOf(
        "emp1" to FullTimeEmployee("Alice", baseSalary = 3000.0, bonus = 500.0),
        "emp2" to FullTimeEmployee("Bob", baseSalary = 2000.0, bonus = 700.0),
        "emp3" to PartTimeEmployee("Mary", hourlyRate = 20.0, hoursWorked = 80.0)
    )
    for ((id, employee) in employees) {
        println("$id - ${employee.getNames()} salary: ${employee.calculateSalary()}")
    }
}