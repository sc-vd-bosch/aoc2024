package org.example.day17.b

import kotlin.math.pow

fun main() {
    val program = "2,4,1,1,7,5,4,0,0,3,1,6,5,5,3,0".split(",").map { s -> s.toLong() }

    println(findAForOutputEqualToProgram(program, program))

}

fun findAForOutputEqualToProgram(program: List<Long>, target: List<Long>) : Long {
    var aStart = if (target.count() == 1) {
        0
    } else {
        8 * findAForOutputEqualToProgram(program, target.subList(1, target.count()))
    }

    while( runProgram(program, aStart) != target) {
        aStart++
    }

    return aStart
}


fun runProgram(program: List<Long>, aStart: Long): List<Long> {
    var i = 0

    var a = aStart
    var b = 0L
    var c = 0L

    val output = mutableListOf<Long>()
    while( i in program.indices) {
        val op = OpCode.entries[program[i].toInt()]
        val operand = program[i+1]
        when(op) {
            OpCode.adv ->  a = (a / 2.0.pow(comboOperand(operand, a, b, c).toDouble())).toLong()
            OpCode.bxl -> b = b.xor(operand)
            OpCode.bst -> b = comboOperand(operand, a, b, c) % 8
            OpCode.jnz -> if(a != 0L) i = operand.toInt()
            OpCode.bxc -> b = b.xor(c)
            OpCode.out -> output.add(comboOperand(operand, a, b, c) % 8)
            OpCode.bdv -> b = (a / 2.0.pow(comboOperand(operand, a, b, c).toDouble())).toLong()
            OpCode.cdv -> c = (a / 2.0.pow(comboOperand(operand, a, b, c).toDouble())).toLong()
        }
        if(!(op == OpCode.jnz && a != 0L)) i += 2
    }
    return output
}

private fun comboOperand(operand: Long, a: Long, b: Long, c: Long): Long {
    return when(operand) {
        0L, 1L, 2L, 3L -> operand
        4L -> a
        5L -> b
        6L -> c
        else -> throw Error("invalid combo operand")
    }
}

enum class OpCode {
    adv, bxl, bst, jnz, bxc, out, bdv, cdv
}