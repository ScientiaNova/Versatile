package com.emosewapixel.pixellib.machines.recipes.components.energy

import com.emosewapixel.pixellib.machines.recipes.components.IRecipeComponentHandler

class EnergyConsumptionHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyConsumptionComponent::class.java
}