package org.example.day4

import java.io.File

fun main() {
    val input = File("src/main/resources/day4a.txt").readLines()

    val grid = Array(input.count()) { "".toCharArray() }
    input.forEachIndexed { index, line -> run { grid[index] = line.toCharArray() } }

    var xmases = 0

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            xmases += findXmasesB(grid, x, y)
        }
    }

    println(xmases)
}

fun findXmasesB(grid: Array<CharArray>, x: Int, y: Int): Int {
    if (grid[y][x] != 'A' || !isInBoundsB(grid, y + 1, x + 1) || !isInBoundsB(grid, y - 1, x - 1)) {
        return 0
    }

    //       \
    val toRightBottom = (grid[y - 1][x - 1] == 'M' && grid[y + 1][x + 1] == 'S')
    val toLeftTop = (grid[y - 1][x - 1] == 'S' && grid[y + 1][x + 1] == 'M')

    //       /
    val toRightTop = (grid[y + 1][x - 1] == 'M' && grid[y - 1][x + 1] == 'S')
    val toLeftBottom = (grid[y + 1][x - 1] == 'S' && grid[y - 1][x + 1] == 'M')

    if ((toRightBottom || toLeftTop) && (toRightTop || toLeftBottom)) {
        return 1
    }

    return 0
}

fun isInBoundsB(grid: Array<CharArray>, x: Int, y: Int): Boolean {
    return y >= 0 && y < grid.size && x >= 0 && x < grid[y].size
}
