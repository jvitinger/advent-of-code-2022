import java.io.FileReader

fun main(args: Array<String>) {
    val lines = FileReader("input07.txt").readLines()

    val root = Dir(null, "/")
    buildTree(root, lines)
    printTree(root, 0)

    // Part 1
    println("Dirs smaller than 100000 total size: ${sumDirsWithPredicate(root) { it.size <= 100000 }}")

    //Part 2
    val diskSpace = 70000000L
    val diskNeeded = 30000000L
    val neededToDelete = diskNeeded - (diskSpace - root.size)
    val toDelete: Dir? = findSmallestDirAtLeast(root, neededToDelete)
    println("Needed to delete: $neededToDelete, dir to delete: ${toDelete?.name} ${toDelete?.size}")
}

fun findSmallestDirAtLeast(root: Dir, atLeast: Long): Dir? {
    val subDirs = root.children.filterIsInstance<Dir>().filter { it.size > atLeast }
    return if (subDirs.isEmpty()) if (root.size > atLeast) root else null
    else subDirs.mapNotNull { findSmallestDirAtLeast(it, atLeast) }.minBy {
        it.size - atLeast
    }
}

fun sumDirsWithPredicate(root: Dir, predicate: (Dir) -> Boolean): Long {
    return ((if (predicate(root)) root.size else 0)) +
            root.children.filterIsInstance<Dir>().sumOf {
                sumDirsWithPredicate(it, predicate)
            }
}

fun printTree(root: Node, depth: Int) {
    repeat(depth) {
        print("    ")
    }
    if (root is File) {
        println("FILE ${root.name} ${root.size}")
    }
    if (root is Dir) {
        println("DIR ${root.name} ${root.size}")
        root.children.forEach {
            printTree(it, depth + 1)
        }
    }
}

fun buildTree(root: Dir, lines: List<String>) {
    var currentDir = root
    var i = 0
    while (i < lines.size) {
        val line = lines[i++]
        when {
            line.startsWith("$ cd /") -> {
                currentDir = root
            }

            line.startsWith("$ cd ..") -> {
                currentDir = currentDir.parent ?: root
            }

            line.startsWith("$ cd") -> {
                val dirName = line.split(" ")[2]
                currentDir = currentDir.children.filterIsInstance<Dir>().first { it.name == dirName }
            }

            line.startsWith("$ ls") -> {
                while (i < lines.size) {
                    val lsLine = lines[i++]
                    when {
                        lsLine.startsWith("$") -> {
                            i--
                            break
                        }

                        lsLine.startsWith("dir") -> {
                            val dirName = lsLine.split(" ")[1]
                            currentDir.children.add(Dir(currentDir, dirName))
                        }

                        else -> {
                            val (size, fileName) = lsLine.split(" ")
                            currentDir.children.add(File(currentDir, fileName, size.toLong()))
                        }
                    }
                }
            }

            else -> throw RuntimeException("Cannot parse: $i: $line")
        }
    }
}

open class Node(val parent: Dir?, val name: String) {

    open val size = 0L
}

class File(parent: Dir, name: String, override var size: Long) : Node(parent, name)

class Dir(parent: Dir?, name: String) : Node(parent, name) {

    val children = mutableSetOf<Node>()

    private var cachedSize = -1L

    override val size: Long
        get() = if (cachedSize >= 0) {
            cachedSize
        } else {
            cachedSize = children.sumOf { it.size }
            cachedSize
        }
}