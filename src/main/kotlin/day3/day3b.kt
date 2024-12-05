package org.example.day3

import java.io.File

fun main() {
    val input = File("src/main/resources/day3a.txt").readLines()

    val regex = Regex("""mul\(([0-9]+),([0-9]+)\)""")

    val output = findEverythingInLineB(input.joinToString(), regex)

    println(output)
}

fun findEverythingInLineB(s: String, r: Regex): Int {
    val doRegex = Regex("""do\(\)""")
    val dontRegex = Regex("""don't\(\)""")

    val doMatches = doRegex.findAll(s).map { m ->  m.range.first }.toList()
    val dontMatches = dontRegex.findAll(s).map { m -> m.range.first }.toList()

    val matches = r.findAll(s)
    var output = 0

    matches.forEach { m ->
        val (a, b) = m.destructured
        if( doMul(m.range.first, doMatches, dontMatches) ) output += a.toInt() * b.toInt()
    }
    return output
}

internal fun doMul(first: Int, doMatches: List<Int>, dontMatches: List<Int>): Boolean {
    val closestDo = doMatches.lastOrNull { m -> m < first }
    val closestDont = dontMatches.lastOrNull  { m -> m < first }

    if( closestDont == null ) return true
    if( closestDo == null ) return false
    return closestDo > closestDont
}
