package org.example.day5

import java.io.File

fun main() {
    val rulesInput = File("src/main/resources/day5a-rules.txt").readLines()

    val rules = rulesInput
        .map { s -> s.split('|') }
        .map { strings -> Rule(strings[0].toInt(), strings[1].toInt()) }

    val printsInput = File("src/main/resources/day5a-prints.txt").readLines()
    val output = printsInput.map { s -> Print.new(s) }
        .filter { print -> !print.isValid(rules) }
        .map { print -> print.makeValid(rules) }
        .map { print -> print.middlePage() }
        .reduce { acc, i -> acc + i }

    println(output)
}