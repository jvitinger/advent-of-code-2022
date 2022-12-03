import java.io.FileReader

fun main(args: Array<String>) {

    Shape.values().forEach { shape ->
        Shape.values().forEach {
            if (shape.beats(it)) println("${shape.name} beats ${it.name}")
        }
    }

    val lines = FileReader("input02.txt").readLines()

    var totalScore = 0
    lines.forEach {
        val (opponentChar, myChar) = it.split(" ")
        val opponentShape = Shape.fromChar(opponentChar)
        val myShape = Shape.fromChar(myChar)
        val score = myShape.score + when {
            opponentShape.beats(myShape) -> 0
            myShape.beats(opponentShape) -> 6
            else -> 3
        }
        println("$it - $opponentShape $myShape: $score")
        totalScore += score
    }
    println(totalScore)

}

enum class Shape(val score: Int, val char1: String, val char2: String) {

    ROCK(1, "A", "X"),
    PAPER(2, "B", "Y"),
    SCISSORS(3, "C", "Z");

    fun beats(shape: Shape) = ordinal == (shape.ordinal + 1) % 3

    companion object {
        fun fromChar(char: String) = Shape.values().first { char == it.char1 || char == it.char2 }
    }
}