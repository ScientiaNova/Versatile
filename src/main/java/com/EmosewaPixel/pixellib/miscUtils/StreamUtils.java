package com.EmosewaPixel.pixellib.miscUtils;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

//This class contains useful functions for working with Streams
public class StreamUtils {
    public static Integer[] getArrayFromRange(int start, int end) {
        return (Integer[]) IntStream.range(start, end).boxed().toArray();
    }

    public static void repeat(int times, IntConsumer function) {
        IntStream.range(0, times).forEach(function);
    }

    public static boolean isNotNull(Object o) {
        return o != null;
    }

    public static int sum(int n1, int n2) {
        return n1 + n2;
    }
}