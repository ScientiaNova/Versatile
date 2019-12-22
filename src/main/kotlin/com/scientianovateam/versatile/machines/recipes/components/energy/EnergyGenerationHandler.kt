package com.scientianovateam.versatile.machines.recipes.components.energy

import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler

class EnergyGenerationHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyGenerationComponent::class.java
    override val serializer = EnergyGenerationSerializer
}