package com.scientianova.versatile.machines.recipes.components.stats

import com.scientianova.versatile.machines.recipes.components.IRecipeComponentHandler

class TimeHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = TimeComponent::class.java
}