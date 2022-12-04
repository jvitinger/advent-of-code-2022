import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input03.txt").readLines()

    var total = 0
    val groupOfElves = mutableSetOf<Set<Char>>()
    val allChars = ('a'..'z') + ('A'..'Z')
    lines.forEach {
        groupOfElves.add(it.toSet())
        println(it)
        if (groupOfElves.size == 3) {
            val groupBadge = groupOfElves
                .fold(allChars.toSet()) { aggregated, newGroup -> aggregated.intersect(newGroup) }
                .first()
            val priority = allChars.indexOf(groupBadge) + 1
            println("    group badge: $groupBadge, priority: $priority")
            total += priority
            groupOfElves.clear()
        }
    }
    println(total)
}