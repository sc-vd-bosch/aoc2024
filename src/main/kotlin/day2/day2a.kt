package org.example.day2

import java.io.File

class Report(val levels: List<Int>) {

    fun isSafe(): Boolean {
        if (levels.count() <= 1){
            return true
        }

        val ascending = levels[0] < levels[1]

        for (i in 0 until levels.size - 1) {
            val diff = levels[i + 1] - levels[i]
            if (ascending && (diff <= 0 || diff > 3) || !ascending && (diff >= 0 || diff < -3)) {
                return false
            }
        }
        return true
    }

    companion object {
        fun new(s: String) = Report(s.split(' ').map { p -> p.toInt() })
    }

}

fun main() {
    val input = File("src/main/resources/day2a.txt").readLines()

    val output = input.map { s -> Report.new(s) }.count { r -> r.isSafe() }

    println(output)
}
