package org.example.day17.a

import java.io.File
import kotlin.math.pow
import kotlin.math.truncate

fun main() {
    val input = File("src/main/resources/day17a.txt").readLines()
    val a = Register.parse(input[0])
    val b = Register.parse(input[1])
    val c = Register.parse(input[2])

    val p = Program.parse(input[4])


    val computer = Computer(a,b,c,p)

    computer.print()

    computer.runProgram()

    computer.print()




    println(Int.MAX_VALUE / 24)
}

class Computer(val a: Register, val b: Register, val c: Register, val p: Program) {

    val output = mutableListOf<Int>()

    fun outputEqualsProgram() = output.joinToString(",") == p.p.joinToString(",")

    fun print() {
        println("Register A: ${a.value}")
        println("Register B: ${b.value}")
        println("Register C: ${c.value}")
        println()
        println("Program: ${p.p}")
        println()
        println("Output: ${output.joinToString(",")}")
    }

    fun runProgram() {
        var i = 0
        while( i  < p.p.count()) {
            val op = OpCode.entries[p.p[i]]
            val operand = p.p[i+1]
            if(op == OpCode.jnz && a.value != 0)  {
                i = operand
            }else {
                executeOp(op, operand)
                i += 2
            }
        }
    }

    private fun executeOp(op: OpCode, operand: Int) {
        when(op) {
            OpCode.adv ->  a.value = adv(comboOperand(operand))
            OpCode.bxl -> b.value = operand.toLong().xor(b.value.toLong()).toInt()
            OpCode.bst -> b.value = comboOperand(operand) % 8
            OpCode.jnz -> 1 == 1 // do nothing
            OpCode.bxc -> b.value = c.value.toLong().xor(b.value.toLong()).toInt()
            OpCode.out -> output.add(comboOperand(operand) % 8)
            OpCode.bdv -> b.value = adv(comboOperand(operand))
            OpCode.cdv -> c.value = adv(comboOperand(operand))
        }


    }

    private fun adv(comboOp: Int): Int {
        return truncate(a.value.toDouble() / 2.0.pow(comboOp)).toInt()
    }

    private fun comboOperand(operand: Int): Int {
       return when(operand) {
            0, 1, 2, 3 -> operand
            4 -> a.value
            5 -> b.value
            6 -> c.value
            else -> throw Error("invalid combo operand")
        }
    }
}

class Register(var value: Int) {

    companion object {
        fun parse(input: String): Register {
            val result = input.takeLastWhile { c -> c.isDigit() }

            return Register(result.toInt())
        }
    }
}

class Program(val p: List<Int>) {

    companion object {
        fun parse(input: String): Program {
            return Program(input.drop(9).split(',').map { s -> s.toInt() })
        }
    }
}

enum class OpCode {
    adv, bxl, bst, jnz, bxc, out, bdv, cdv
}