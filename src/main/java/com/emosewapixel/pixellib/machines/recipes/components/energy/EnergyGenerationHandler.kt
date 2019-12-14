package com.emosewapixel.pixellib.machines.recipes.components.energy

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler

class EnergyGenerationHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyGenerationComponent::class.java
}