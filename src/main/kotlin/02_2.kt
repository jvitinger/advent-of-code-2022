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
        val (opponentChar, result) = it.split(" ")
        val opponentShape = Shape.fromChar(opponentChar)
        val myShape = when (result) {
            "X" -> Shape.values().first { shape -> opponentShape.beats(shape) } // I should loose
            "Y" -> opponentShape                                                // Draw
            "Z" -> Shape.values().first { shape -> shape.beats(opponentShape) } // I should win
            else -> throw IllegalArgumentException()
        }
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