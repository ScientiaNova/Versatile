package com.emosewapixel.pixellib.machines.recipes.components

class EnergyPerTickHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyPerTickComponent::class.java
}