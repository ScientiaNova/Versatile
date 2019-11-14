package com.emosewapixel.pixellib.extensions

import net.minecraft.util.NonNullList

fun <T : Any> Iterable<T>.toNoNullList(): NonNullList<T> {
    val list = NonNullList.create<T>()
    forEach { list.add(it) }
    return list
}