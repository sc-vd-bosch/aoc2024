package org.example.day11.b

import java.io.File

fun main() {
    val input = File("src/main/resources/day11a.txt").readLines()

    val stones = mutableListOf<Stone>()

    val row = input[0]
    row.split(' ').forEach { s -> stones.add(Stone(s.toLong())) }

    val stoneRow = StoneRow(stones)

    var output = stoneRow.blink(75)

    println(output)
}

data class Key(val times: Int, val value: Long)

class StoneRow(var stones: List<Stone>){

    val map = mutableMapOf<Key, Long>()

    fun print() {
        stones.forEach { s -> run {
            print(' ')
            print(s.value)
        } }
        println()
    }

    fun blink(times: Int): Long {
       return stones.sumOf { s -> blink(times, s) }
    }

    fun blink(times: Int, stone: Stone): Long {
        val cachedResult = map.getOrDefault(Key(times, stone.value), null)
        if (cachedResult != null) {
            return cachedResult
        }
        val result = stone.blink()
        if(times == 1) {
            return result.count().toLong()
        }
        val resultCount = result.sumOf { s -> blink(times-1, s) }

        map[Key(times,stone.value)] = resultCount

        return resultCount
    }
}

class Stone(val value : Long) {
    fun blink(): List<Stone> {
        if (value == 0L) {
            return listOf(Stone(1L))
        }
        val stringValue = value.toString()
        if (stringValue.count() % 2 == 0){
            val half = stringValue.count()/2
            val value1 = stringValue.substring(0..<half)
            val value2 = stringValue.substring(half..<stringValue.count())
            return listOf(Stone(value1.toLong()), Stone(value2.toLong()) )
        }

        return listOf(Stone(value * 2024L))
    }
}
