package com.scientianovateam.versatile.common.math

class Graph<T> {
    private val adjacent = mutableMapOf<T, MutableList<T>>()

    fun add(vertex: T) {
        if (vertex in adjacent) return
        adjacent[vertex] = mutableListOf()
    }

    /**
     * @param from the dependency
     * @param to the dependent
     */
    fun add(from: T, to: T) {
        add(from)
        add(to)
        adjacent[from]?.add(to)
    }

    fun remove(from: T, to: T) {
        if (from !in adjacent || to !in adjacent)
            error("Can't remove nonexistent edge $from -> $to")
        adjacent[from]?.remove(to)
    }

    fun topologicalSort(): List<T>? {
        val degree = indegrees.toMutableMap()
        val zeros = degree.filterValues { value -> value == 0 }.keys.toMutableList()
        val result = mutableListOf<T>()
        while (zeros.isNotEmpty()) {
            val current = zeros.removeAt(0)!!
            result.add(current)
            adjacent[current]?.forEach { adj ->
                degree[adj] = degree[adj]?.minus(1) ?: 0
                if (degree[adj] == 0) zeros.add(adj)
            }
        }
        return if (result.size == adjacent.size) result
        else null
    }

    val directedAcyclic: Boolean get() = topologicalSort() != null

    val outdegrees: Map<T, Int>
        get() = adjacent.mapValues { (_, value) -> value.size }

    val indegrees: Map<T, Int>
        get() {
            val result = adjacent.mapValues { 0 }.toMutableMap()
            adjacent.values.forEach { value ->
                value.forEach { to -> result[to] = (result[to] ?: 0) + 1 }
            }
            return result
        }
}