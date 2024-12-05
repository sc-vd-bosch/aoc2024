package org.example.day2

import java.io.File

fun main() {
    val input = File("src/main/resources/day2a.txt").readLines()

    val output = input.map { s -> Report.new(s) }.count { r -> r.isSafe() || safeReportPossible(r) }

    println(output)
}

private fun safeReportPossible(r: Report): Boolean {
    for (i in r.levels.indices) {
        val newLevels = r.levels.toMutableList()
        newLevels.removeAt(i)
        if (Report(newLevels).isSafe()) {
            return true
        }
    }
    return false
}
