package com.emosewapixel.pixellib.machines.recipes.components.stats

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler

class EnergyPerTickHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyPerTickComponent::class.java
}