package org.example.day19.b

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

    println("amount of designs ${designs.count()}")

    var possibleDesigns = 0L
    var cachedDesigns = mutableMapOf<String, Long>()

    designs.forEach { d -> run {
        println("Starting for design ${d.originalPattern}")
        val possibleForDesign = d.isPossible(cachedDesigns)
        possibleDesigns += possibleForDesign

        println("+ $possibleForDesign = $possibleDesigns")
    } }

//    println(designs.sumOf { d -> d.isPossible() })

}

class Design(val originalPattern: String, private val possiblePatterns: List<String>) {

    fun isPossible(cache: MutableMap<String,Long>): Long {
        return isPossible(originalPattern, cache)
    }

    private fun isPossible(pattern: String, cache: MutableMap<String,Long>): Long {
        //look up value in cache
        val cachedResult = cache.getOrDefault(pattern, null)
        if (cachedResult != null) {
            return cachedResult
        }

        // end of pattern is reached, design is possible
        if(pattern.isEmpty()) {
            cache[pattern] = 1L
            return 1L
        }

        // look for possible starting patterns
        val subPatterns = possiblePatterns.filter { s -> patternStartsWith(pattern, s) }
        // no matching patterns, design not possible
        if (subPatterns.isEmpty()) {
            cache[pattern] = 0L
            return 0L
        }

        // for each matching pattern, find number of possible designs
        val numPossibleDesigns = subPatterns.map{ s -> pattern.drop(s.length) }.sumOf { s -> isPossible(s, cache) }
        cache[pattern] = numPossibleDesigns
        return numPossibleDesigns
    }

    fun patternStartsWith(pattern: String, sub: String): Boolean {
        return pattern.startsWith(sub)
    }
}

data class Key(val pattern: String)