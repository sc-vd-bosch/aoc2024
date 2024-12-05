package org.example.day3

import java.io.File

fun main() {
    val input = File("src/main/resources/day3a.txt").readLines()

    val regex = Regex("""mul\(([0-9]+),([0-9]+)\)""")

    var output = 0

    input.forEach { s -> output += findEverythingInLine(s, regex) }

    println(output)
}

fun findEverythingInLine(s: String, r: Regex): Int {
    val matches = r.findAll(s)
    var output = 0

     matches.forEach { m ->
            val (a, b) = m.destructured
            output += a.toInt() * b.toInt()
    }
    return output
}