package org.example.day19.a

import java.io.File

fun main() {
    val input = File("src/main/resources/day19a.txt").readLines()

    val patterns = input[0].split(",").map { s -> s.trim() }

    val designs = mutableListOf<Design>()

    for(i in input.indices) {
        if(i > 1) {
            designs.add(Design(input[i], patterns))
        }
    }

    println(designs.count { d -> d.isPossible() })

}

class Design(private val originalPattern: String, private val possiblePatterns: List<String>) {

    fun isPossible(): Boolean {
        return isPossible(originalPattern)
    }

    private fun isPossible(pattern: String): Boolean {
        if(pattern.isEmpty()) return true
        val subPatterns = possiblePatterns.filter { s -> patternStartsWith(pattern, s) }
        if (subPatterns.isEmpty()) {
            return false
        }
        return subPatterns.map{ s -> pattern.drop(s.length) }.any { s -> isPossible(s) }
    }

    fun patternStartsWith(pattern: String, sub: String): Boolean {
        return pattern.startsWith(sub)
    }
}