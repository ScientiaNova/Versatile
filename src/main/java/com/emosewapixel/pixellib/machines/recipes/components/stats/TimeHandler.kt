package com.emosewapixel.pixellib.machines.recipes.components.stats

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler

class TimeHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = TimeComponent::class.java
}