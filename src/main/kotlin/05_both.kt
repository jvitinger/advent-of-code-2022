import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input05.txt").readLines()

    val stacks = MutableList(9) { ArrayList<Char>() }

    fun printStack(index: Int) {
        print("Stack ${index + 1} ")
        stacks[index].forEach { print(it) }
        println()
    }

    lines.forEachIndexed { lineNumber, line ->
        if (line.contains("[")) {
            // Part one - initial stacks
            stacks.forEachIndexed { index, stack ->
                line.substring(index * 4, ((index + 1) * 4).coerceAtMost(line.length)).firstOrNull { it in 'A'..'Z' }
                    ?.let {
                        stack.add(it)
                    }
            }
        } else if (line.contains("1   2")) {
            // Part two - stack numbers - just print what we collected
            stacks.indices.forEach { printStack(it) }
        } else if (line.contains("move")) {
            // Part three - move...
            val (count, from, to) = line.split(" ").mapNotNull { it.toIntOrNull() }
            val crates = stacks[from - 1].take(count)
            stacks[from - 1] = ArrayList(stacks[from - 1].drop(count))
            print("$lineNumber: Move $count from $from to $to - ")
            crates.forEach { print(it) }
            println()
            // This is a variant for part 1
            stacks[to - 1].addAll(0, crates.reversed())
            // This is a variant for part 2
            // stacks[to - 1].addAll(0, crates)
            printStack(from - 1)
            printStack(to - 1)
        }
    }
    println("Result: ")
    stacks.indices.forEach { printStack(it) }
    stacks.forEach { print(it.first()) }
}


