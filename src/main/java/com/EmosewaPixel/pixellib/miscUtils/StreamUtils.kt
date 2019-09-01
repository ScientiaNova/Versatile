package com.EmosewaPixel.pixellib.miscutils

import java.util.function.IntConsumer
import java.util.stream.IntStream

//This class contains useful functions for working with Streams
object StreamUtils {
    @JvmStatic
    fun getArrayFromRange(start: Int, end: Int): Array<Int> = IntStream.range(start, end).boxed().toArray() as Array<Int>

    @JvmStatic
    fun repeat(times: Int, function: IntConsumer) {
        IntStream.range(0, times).forEach(function)
    }

    @JvmStatic
    fun sum(n1: Int, n2: Int) = n1 + n2
}