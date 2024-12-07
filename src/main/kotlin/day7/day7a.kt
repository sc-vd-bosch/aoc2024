package org.example.day7

import java.io.File
import kotlin.math.absoluteValue

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val input = File("src/main/resources/day7a-example.txt").readLines()

    val bridge = mutableListOf<Calibration>()

    input.map { s -> s.split(":") }
        .forEach { s ->
            val list = s[1].split(" ").filter{ s -> s.isNotEmpty() }.map { s -> s.toInt() }
            bridge.add(Calibration(s[0].toInt(),list))
        }


    var output = 0

    bridge.forEach { c -> c.print() }
    bridge.filter{ c -> c.valuePossible() }.forEach { c -> output += c.testValue }

    println(output)
}

class Calibration(val testValue: Int, val list: List<Int>) {

    fun valuePossible(): Boolean {
        return when(list.count()) {
            0 -> true
            1 -> list[0] == testValue
            2 -> list[0] + list[1] == testValue || list[0] * list[1] == testValue
            else -> false
        }

    }

    fun print() {
        println("$testValue: $list")
    }
}

enum class Operator {
    ADD, MULTIPLY
}