import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input04.txt").readLines()

    var total = 0
    lines.forEach {
        val ranges = it.split(",")
        val range1 = ranges[0].toIntRange()
        val range2 = ranges[1].toIntRange()
        if (range1.intersect(range2).isNotEmpty()) {
            total++
            println(it)
        }
    }
    println(total)
}