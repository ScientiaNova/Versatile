package com.scientianovateam.versatile.common.extensions

import com.google.common.collect.HashBasedTable
import com.google.common.collect.HashBiMap
import com.google.common.collect.HashMultimap
import com.google.common.collect.HashMultiset
import net.minecraft.util.NonNullList

fun <T : Any> Iterable<T>.toNoNullList() = fold(NonNullList.create<T>()) { list, e -> list.apply { add(e) } }

fun <T> Iterable<T>.toMultiset() = fold(HashMultiset.create<T>()) { set, e -> set.apply { add(e) } }

fun <K, V> Iterable<Pair<K, V>>.toMultimap() = fold(HashMultimap.create<K, V>()) { list, pair -> list.apply { put(pair.first, pair.second) } }

fun <K, V> Iterable<Pair<K, V>>.toBimap() = fold(HashBiMap.create<K, V>()) { list, pair -> list.apply { put(pair.first, pair.second) } }

fun <R, C, V> Iterable<Triple<R, C, V>>.toTable() = fold(HashBasedTable.create<R, C, V>()) { table, triple -> table.apply { put(triple.first, triple.second, triple.third) } }