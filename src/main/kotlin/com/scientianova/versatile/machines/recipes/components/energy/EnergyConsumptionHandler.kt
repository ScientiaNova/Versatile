package com.scientianova.versatile.machines.recipes.components.energy

import com.scientianova.versatile.machines.recipes.components.IRecipeComponentHandler

class EnergyConsumptionHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyConsumptionComponent::class.java
}