import java.io.FileReader

fun main(args: Array<String>) {
    val input = FileReader("input06.txt").readText()

    val sizeOFMarker = 14

    val deque = ArrayDeque<Char>()
    input.forEachIndexed { index, char ->
        deque.addLast(char)
        if (deque.size > sizeOFMarker) {
            deque.removeFirst()
        }
        if (deque.toSet().size == sizeOFMarker) {
            println("${index + 1}:")
            deque.forEach { print(it) }
            return
        }
    }
}


