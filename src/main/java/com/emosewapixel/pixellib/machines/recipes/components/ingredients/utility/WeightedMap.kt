package com.emosewapixel.pixellib.machines.recipes.components.ingredients.utility

import java.util.*
import kotlin.random.Random

class WeightedMap<V>(other: Map<Int, V> = emptyMap()) : Map<Int, V> {
    private val map: NavigableMap<Int, V>

    var maxWeight: Int
        private set

    init {
        var total = 0
        map = if (other is WeightedMap<V>) {
            total = other.maxWeight
            TreeMap(other)
        } else
            TreeMap(other.mapKeys { (k, _) ->
                val value = total + k
                total += k
                value
            })
        maxWeight = total
    }

    val random get() = this[Random.nextInt(maxWeight)]

    override operator fun get(key: Int): V? = map.higherEntry(key)?.value

    operator fun set(key: Int, value: V) {
        map[key + maxWeight] = value
        maxWeight += key
    }

    operator fun set(key: Float, value: V) {
        map[(key * 100).toInt() + maxWeight] = value
        maxWeight += (key * 100).toInt()
    }

    override fun isEmpty() = map.isEmpty()

    override fun containsKey(key: Int) = this[key] != null

    override fun containsValue(value: V) = value in values

    override val size get() = map.size

    override val keys get() = map.keys

    override val values get() = map.values

    override val entries get() = map.entries

    val weightedEntries: List<Pair<Int, V>>
        get() {
            var counter = 0
            return keys.zip(values) { key, value ->
                val res = key - counter to value
                counter = key
                res
            }
        }
}

fun <K> weightedMapOf(vararg entries: Pair<Int, K>) = WeightedMap(entries.toMap())

@JvmName("fWeightedMapOf")
fun <K> weightedMapOf(vararg entries: Pair<Float, K>) = WeightedMap(entries.toMap().mapKeys { (it.key * 100).toInt() })

operator fun <K> MutableCollection<in WeightedMap<K>>.plusAssign(value: K): Unit = plusAssign(weightedMapOf(1 to value))

operator fun <K> MutableCollection<in WeightedMap<K>>.plusAssign(values: Collection<K>): Unit = plusAssign(values.map { weightedMapOf(1 to it) })

fun <K> Map<Int, K>.toWeightedMap() = WeightedMap(this)