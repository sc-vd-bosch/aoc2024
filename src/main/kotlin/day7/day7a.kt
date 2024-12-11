package org.example.day7

import java.io.File
import java.io.IO.println

fun main() {
    val input = File("src/main/resources/day7a.txt").readLines()

    val bridge = mutableListOf<Calibration>()

    input.map { s -> s.split(":") }
        .forEach { s ->
            val list = s[1].split(" ").filter{ s -> s.isNotEmpty() }.map { s -> s.toLong() }
//            println("${s[0].toLong()}: $list")
            println(list.count())
            bridge.add(Calibration(s[0].toLong(),list))
        }

    var output = 0L

//    bridge.forEach { c -> c.print() }
    bridge.filter{ c -> c.valuePossible() }.forEach { c -> output += c.testValue }

    println(output)
}

class Calibration(val testValue: Long, val list: List<Long>) {

    fun valuePossible(): Boolean {
        print()
        return something(testValue, list, Operator.ADD)
    }

    private fun something(value: Long, list2: List<Long>, op: Operator): Boolean {
        println("in something: $value: $list2,  $op")

        if (list2.count() == 1) {
            return list2[0] == value
        }

        if (value < 0) {
            return false
        }

        val lastValue = list2[list2.count()-1]

        val newDividedValue = value / lastValue

        val sublist = list2.subList(0, list2.count() - 1)
        val isConcatinated = value.toString().endsWith(lastValue.toString()) && value.toString().length != lastValue.toString().length

        println(isConcatinated)

        return value ==  newDividedValue * lastValue && something(newDividedValue, sublist, Operator.MUL)
                || something(value - lastValue, sublist, Operator.ADD)
                || isConcatinated
                    && something(value.toString().dropLast(lastValue.toString().count()).toLong(), sublist, Operator.CONCAT)
    }

    fun print() {
        println("$testValue: $list")
    }
}

enum class Operator {
    ADD, MUL, CONCAT
}