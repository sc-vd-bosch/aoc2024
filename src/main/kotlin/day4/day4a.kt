package org.example.day4

import java.io.File

fun main() {
    val input = File("src/main/resources/day4a.txt").readLines()

    val grid = Array(input.count()) { "".toCharArray() }
    input.forEachIndexed { index, line -> run { grid[index] = line.toCharArray() } }

    var xmases = 0

    for (y in grid.indices) {
        for (x in grid[y].indices) {
            xmases += findXmasesA(grid, x, y)
        }
    }

    println(xmases)
}

fun findXmasesA(grid: Array<CharArray>, x: Int, y: Int): Int {
    if (grid[y][x] != 'X') {
        return 0
    }

    var xmases = 0

    // Naar rechts
    if (isInBoundsA(grid, y, x + 3) && grid[y][x + 1] == 'M' && grid[y][x + 2] == 'A' && grid[y][x + 3] == 'S') {
        xmases++
    }

    // Naar links
    if (isInBoundsA(grid, y, x - 3) && grid[y][x - 1] == 'M' && grid[y][x - 2] == 'A' && grid[y][x - 3] == 'S') {
        xmases++
    }

    // Naar onder
    if (isInBoundsA(grid, y + 3, x) && grid[y + 1][x] == 'M' && grid[y + 2][x] == 'A' && grid[y + 3][x] == 'S') {
        xmases++
    }

    // Naar boven
    if (isInBoundsA(grid, y - 3, x) && grid[y - 1][x] == 'M' && grid[y - 2][x] == 'A' && grid[y - 3][x] == 'S') {
        xmases++
    }

    // Naar rechtsonder
    if (isInBoundsA(
            grid,
            y + 3,
            x + 3
        ) && grid[y + 1][x + 1] == 'M' && grid[y + 2][x + 2] == 'A' && grid[y + 3][x + 3] == 'S'
    ) {
        xmases++
    }

    // Naar rechtsboven
    if (isInBoundsA(
            grid,
            y - 3,
            x + 3
        ) && grid[y - 1][x + 1] == 'M' && grid[y - 2][x + 2] == 'A' && grid[y - 3][x + 3] == 'S'
    ) {
        xmases++
    }

    // Naar linksonder
    if (isInBoundsA(
            grid,
            y + 3,
            x - 3
        ) && grid[y + 1][x - 1] == 'M' && grid[y + 2][x - 2] == 'A' && grid[y + 3][x - 3] == 'S'
    ) {
        xmases++
    }

    // Naar linksboven
    if (isInBoundsA(
            grid,
            y - 3,
            x - 3
        ) && grid[y - 1][x - 1] == 'M' && grid[y - 2][x - 2] == 'A' && grid[y - 3][x - 3] == 'S'
    ) {
        xmases++
    }

    return xmases
}

fun isInBoundsA(grid: Array<CharArray>, x: Int, y: Int): Boolean {
    return y >= 0 && y < grid.size && x >= 0 && x < grid[y].size
}
