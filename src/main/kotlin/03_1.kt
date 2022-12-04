import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input03.txt").readLines()

    var total = 0
    lines.forEach {
        val part1 = it.take(it.length / 2)
        val part2 = it.takeLast(it.length / 2)
        val commonChar = part1.toSet().intersect(part2.toSet()).first()
        val priority = (('a'..'z') + ('A'..'Z')).indexOf(commonChar) + 1
        println("$it - common item: $commonChar, priority: $priority")
        total += priority
    }
    println(total)
}