package com.emosewapixel.pixellib.extensions

import com.google.common.collect.Table

operator fun Table.Cell<*, *, *>.component1() = rowKey
operator fun Table.Cell<*, *, *>.component2() = columnKey
operator fun Table.Cell<*, *, *>.component3() = value

operator fun <R, C, V> Table<R, C, V>.set(row: R, column: C, value: V) = put(row, column, value)
operator fun <R, C, V> Table<R, C, V>.plusAssign(other: Table<R, C, V>) {
    putAll(other)
}

operator fun <R, C, V> Table<R, C, V>.plusAssign(t: Triple<R, C, V>) {
    put(t.first, t.second, t.third)
}

operator fun <R, C, V> Table<R, C, V>.minusAssign(p: Pair<R, C>) {
    remove(p.first, p.second)
}

inline fun <R, C, V> Table<R, C, V>.forEach(function: (Table.Cell<R, C, V>) -> Unit) = cellSet().forEach(function)