import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input04.txt").readLines()

    val total = lines.count { line ->
        line.split(",").map { it.toIntRange().toSet() }.reduce { acc, range -> acc.intersect(range) }.isNotEmpty()
    }
    println(total)
}