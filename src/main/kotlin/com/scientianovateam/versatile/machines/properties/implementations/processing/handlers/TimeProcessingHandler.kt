package com.scientianovateam.versatile.machines.properties.implementations.processing.handlers

import com.scientianovateam.versatile.machines.properties.implementations.primitives.integers.TEIntegerProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.time.TimeComponent

class TimeProcessingHandler(val property: TEIntegerProperty) : IProcessingHandler {
    override fun isProcessing() = property.value > 0

    override fun startProcessingStandard(recipe: Recipe) = property.setValue(1, true)

    override fun startProcessingAutomation(recipe: Recipe) = property.setValue(1, true)

    override fun processTick(recipe: Recipe) = property.setValue(property.value + 1, true)

    override fun shouldFinishProcessing(recipe: Recipe) = property.value >= recipe[TimeComponent::class.java]?.value ?: 0

    override fun finishProcessingStandard(recipe: Recipe) = property.setValue(0, true)

    override fun finishProcessingAutomation(recipe: Recipe) = finishProcessingStandard(recipe)
}