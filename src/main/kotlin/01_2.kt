import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input01.txt").readLines()

    var maxCalories = listOf<Int>()
    var currentElfCalories = 0
    var currentElfIndex = 0
    lines.forEach {
        if (it.isBlank()) {
            currentElfIndex++
            currentElfCalories = 0
        } else {
            val calories = it.toInt()
            currentElfCalories += calories
            maxCalories = (maxCalories + currentElfCalories).sortedDescending().take(3)
        }
    }
    println(maxCalories)
    println(maxCalories.sum())
}