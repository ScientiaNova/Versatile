package com.EmosewaPixel.pixellib.miscutils

//This class contains useful functions for working with Streams
fun getArrayFromRange(start: Int, end: Int): Array<Int> = (start until end).toList().toTypedArray()