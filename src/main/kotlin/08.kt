import java.io.FileReader

fun main(args: Array<String>) {
    val input = FileReader("input08.txt").readLines()

    var forest = buildForest(input)

    repeat(4) {
        println()
        println("================================ $it ================================")
        markVisibleFromLeft(forest)
        forest = rotate90(forest)
    }

    println()
    println("Visible trees: ${forest.sumOf { row -> row.count { tree -> tree.visible } }}")

    repeat(4) {
        println()
        println("================================ $it ================================")
        calculateScenicScoreToLeft(forest)
        forest = rotate90(forest)
    }

    println()
    println("Max scenic score: ${forest.maxOf { row -> row.maxOf { tree -> tree.scenicScore } }}")
}

private fun calculateScenicScoreToLeft(forest: MutableList<MutableList<Tree>>) {
    forest.indices.forEach { i ->
        println()
        forest[i].indices.forEach { j ->
            forest[i][j].scenicScore *= calculateScenicScoreToLeft(forest, i, j)
            print("  ${forest[i][j].scenicScore}  ")
        }
    }
}

fun calculateScenicScoreToLeft(forest: MutableList<MutableList<Tree>>, i: Int, j: Int): Int {
    val baseHeight = forest[i][j].height
    var scenicScore = 0
    (j + 1 until forest[i].size).forEach {
        scenicScore++
        if (forest[i][it].height >= baseHeight) {
            return scenicScore
        }
    }
    return scenicScore
}

private fun buildForest(input: List<String>): MutableList<MutableList<Tree>> {
    val forest: MutableList<MutableList<Tree>> = mutableListOf()
    input.forEach { line ->
        forest.add(mutableListOf())
        line.forEach {
            forest.last().add(Tree(it.toString().toInt()))
        }
    }
    return forest
}

private fun markVisibleFromLeft(forest: MutableList<MutableList<Tree>>) {
    forest.forEach { row ->
        var maxHeight = -1
        println()
        row.forEach { tree ->
            if (tree.height > maxHeight) {
                maxHeight = tree.height
                tree.visible = true
                print(tree.height)
            } else {
                print(" ")
            }
        }
    }
}

fun rotate90(forest: MutableList<MutableList<Tree>>): MutableList<MutableList<Tree>> {
    val transposed = MutableList(forest[0].size) { x ->
        MutableList(forest.size) { y ->
            forest[y][x]
        }
    }
    transposed.indices.forEach { i ->
        transposed[i].reverse()
    }
    return transposed
}

class Tree(
    val height: Int,
    var visible: Boolean = false,
    var scenicScore: Int = 1
)


