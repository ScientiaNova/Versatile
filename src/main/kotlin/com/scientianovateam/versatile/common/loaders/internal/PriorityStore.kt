package com.scientianovateam.versatile.common.loaders.internal

import com.scientianovateam.versatile.Versatile

object PriorityStore {
    // TODO This should be loaded from a config file

    val minimums = mutableMapOf<String, Int>()
    val maximums = mutableMapOf<String, Int>()

    init {
        maximums[Versatile.MOD_ID] = 0 // We are lowest priority
    }
}