package com.scientianova.versatile.machines.properties.implementations.processing.handlers

import com.scientianova.versatile.machines.properties.implementations.energy.TEEnergyOutputProperty
import com.scientianova.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.components.energy.EnergyGenerationComponent

class EnergyOutputProcessingHandler(val property: TEEnergyOutputProperty) : IProcessingHandler {
    override fun canStartProcessingStandard(recipe: Recipe): Boolean {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return true
        return property.value.energyStored + recipeEnergy.value <= property.value.maxEnergyStored
    }

    override fun canStartProcessingAutomation(recipe: Recipe) = canStartProcessingStandard(recipe)

    override fun startProcessingStandard(recipe: Recipe) {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return
        property.value.receiveEnergy(recipeEnergy.value, false)
    }

    override fun startProcessingAutomation(recipe: Recipe) = startProcessingStandard(recipe)

    override fun canContinueProcessing(recipe: Recipe): Boolean {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return true
        return property.value.energyStored + recipeEnergy.value <= property.value.maxEnergyStored
    }

    override fun processTick(recipe: Recipe) {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return
        property.value.receiveEnergy(recipeEnergy.value, false)
    }
}