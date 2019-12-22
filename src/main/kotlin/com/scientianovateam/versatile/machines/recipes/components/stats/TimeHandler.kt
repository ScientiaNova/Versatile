package com.scientianovateam.versatile.machines.recipes.components.stats

import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler

class TimeHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = TimeComponent::class.java
    override val serializer = TimeSerializer
}