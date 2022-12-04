import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input04.txt").readLines()

    var total = 0
    lines.forEach {
        val ranges = it.split(",")
        val range1 = ranges[0].toIntRange()
        val range2 = ranges[1].toIntRange()
        if (range1.isFullyWithin(range2) || range2.isFullyWithin(range1)) {
            total++
            println(it)
        }
    }
    println(total)
}

private fun IntRange.isFullyWithin(range: IntRange) = range.first <= start && range.last >= endInclusive

fun String.toIntRange(): IntRange {
    val (lower, upper) = this.split("-").map { it.toInt() }
    return IntRange(lower, upper)
}