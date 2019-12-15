package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.machines.properties.implementations.primitives.integers.TEIntegerProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeComponent

class TimeProcessingHandler(val property: TEIntegerProperty) : IProcessingHandler {
    override fun isProcessing(recipe: Recipe) = property.value > 0

    override fun startProcessing(recipe: Recipe) = property.setValue(1, true)

    override fun processTick(recipe: Recipe) = property.setValue(property.value + 1, true)

    override fun shouldFinishProcessing(recipe: Recipe) = property.value >= recipe[TimeComponent]?.value ?: 0

    override fun finishProcessing(recipe: Recipe) = property.setValue(0, true)
}