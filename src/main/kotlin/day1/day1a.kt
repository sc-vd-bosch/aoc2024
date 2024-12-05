package org.example.day1

import java.io.File
import kotlin.math.absoluteValue

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val input = File("src/main/resources/day1a.txt").readLines()

    val list1 = mutableListOf<Int>()
    val list2 = mutableListOf<Int>()

    input.map { s -> s.split("   ") }
        .forEach { s ->
            list1.add(s[0].toInt())
            list2.add(s[1].toInt())
        }

    list1.sort()
    list2.sort()

    var output = 0

    list1.forEachIndexed { i, v -> output += difference(v, list2[i]) }

    println(output)
}

fun difference(v1: Int, v2: Int): Int {
    val result = (v1 - v2).absoluteValue
    println(" $v1, $v2 -> $result")
    return result
}
