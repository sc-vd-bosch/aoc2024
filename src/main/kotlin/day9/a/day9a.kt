package org.example.day9.a

import java.io.File
import kotlin.math.absoluteValue

fun main() {
    val input = File("src/main/resources/day9a.txt").readLines()

    val sequence = input[0]
    println(input.count())

    val fileSystem = mutableListOf<String>()
    val fileSystem2 = mutableListOf<String>()

//    println( sequence )

    var numFiles = 0

    sequence.forEachIndexed { i, c -> run {
            if ( i  % 2 == 0) {
               val size = c.digitToInt()
               for (j in 0 until size) {
                   fileSystem.add(c.toString())
                   fileSystem2.add(numFiles.toString())
               }
                numFiles++
            } else {
                val size = c.digitToInt()
                for (j in 0 until size) {
                    fileSystem.add(".")
                    fileSystem2.add(".")
                }
            }
        }
    }

//    println("file system 1: $fileSystem")
//    println("file system 2: $fileSystem2")

//    var indexFirstDot = fileSystem2.indexOfFirst { s -> s == "." }

    var dotGroups = findAllDotGroup(fileSystem2)
    var fileGroup = mutableListOf<Int>()
    for(i in fileSystem2.indices.reversed()) {
//        println("$i : ${fileSystem2[i]}")
        if(dotGroups.first().last() >= i) break
        val fileId = fileSystem2[i]
        if(fileId != ".") {
//            println("not a dot")
            fileGroup.add(i)
            if(i -1 >= 0 && fileSystem2[i-1] == fileId) {
                // continue in group
//                println("continue in group")
            } else {
//                println("else ...")
//                print(dotGroups)

                val dotGroup = dotGroups.firstOrNull { dg -> dg.count() >= fileGroup.count() && dg.last() < fileGroup.first() }
                if(dotGroup != null ){
//                    println("in while $dotGroup, $fileGroup")
                    if (dotGroup.count() >= fileGroup.count()) {
//                        println("move group $fileId with indices $fileGroup to $dotGroup")

                        for (x in 0 until fileGroup.count()) {
//                            println("$dotGroup - $x move $fileId from index ${i + x} to ${dotGroup[x]}")
//                            println("file system 2: ${fileSystem2}")

                            fileSystem2[dotGroup[x]] = fileId
//                            println("file system 2: ${fileSystem2}")

                            fileSystem2[fileGroup[x]] = "."
//                            println("file system 2: ${fileSystem2}")
//                            println("---------------------------------------------------------------")
                        }
                        dotGroups = findAllDotGroup(fileSystem2)
                    }
                }
                fileGroup = mutableListOf()

            }
        }
    }
// file system 2: [0, 0, 9, 9, 2, 1, 1, 1, 7, 7, 7, ., ., ., 4, 4, ., ., 3, 3, 3, ., ., ., ., ., 5, 5, 5, 5, ., 6, 6, 6, 6, ., 8, 8, 8, 8, ., .]
//    println("file system 2: ${fileSystem2}")

    // Find checksum
    var output = 0L

    fileSystem2.forEachIndexed { i, s -> run {
        if(s != ".")  {
            output += i * s.toLong()
        }
    } }

    println(output)
}

fun findAllDotGroup(list: List<String>): List<List<Int>>  {
    val result = mutableListOf<List<Int>>()
    var subresult = mutableListOf<Int>()
    var indexFirstDot = list.indexOfFirst { s -> s == "." }
    for (i in indexFirstDot until list.count()) {
        if (list[i] != "." && subresult.isNotEmpty()) {
            result.add(subresult)
            subresult = mutableListOf()
        }
        if (list[i] == ".") {
            subresult.add(i)
        }
    }
    return result
}

fun print(grid: List<List<Int>>) {
    println()
    for( i in 0 until grid.count()) {
        println(grid[i])
    }
    println()
}
