package org.example.day1

import java.io.File

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

    list1.forEach { v -> output += similarity(v, list2) }

    println(output)
}

fun findIndeces(v: Int, list: List<Int>): List<Int> {
    return list.mapIndexedNotNull{ index, elem -> index.takeIf{ elem == v } }
}

fun similarity(v: Int, list: List<Int>): Int {
    val result = v * findIndeces(v, list).count()
    println(" $v -> $result")
    return result
}
