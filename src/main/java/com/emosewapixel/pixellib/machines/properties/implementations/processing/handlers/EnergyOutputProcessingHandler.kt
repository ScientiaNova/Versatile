package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.machines.properties.implementations.energy.TEEnergyOutputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.energy.EnergyGenerationComponent

class EnergyOutputProcessingHandler(val property: TEEnergyOutputProperty) : IProcessingHandler {
    override fun canStartProcessing(recipe: Recipe): Boolean {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return true
        return property.value.energyStored + recipeEnergy.value <= property.value.maxEnergyStored
    }

    override fun startProcessing(recipe: Recipe) {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return
        property.value.receiveEnergy(recipeEnergy.value, false)
    }

    override fun canContinueProcessing(recipe: Recipe): Boolean {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return true
        return property.value.energyStored + recipeEnergy.value <= property.value.maxEnergyStored
    }

    override fun processTick(recipe: Recipe) {
        val recipeEnergy = recipe[EnergyGenerationComponent::class.java] ?: return
        property.value.receiveEnergy(recipeEnergy.value, false)
    }
}