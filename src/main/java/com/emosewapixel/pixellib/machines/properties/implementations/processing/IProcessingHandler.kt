package com.emosewapixel.pixellib.machines.properties.implementations.processing

import com.emosewapixel.pixellib.machines.recipes.Recipe

interface IProcessingHandler {
    fun canStartProcessing(recipe: Recipe) = true

    fun startProcessing(recipe: Recipe) {}

    fun isProcessing(recipe: Recipe) = true

    fun canContinueProcessing(recipe: Recipe) = true

    fun processTick(recipe: Recipe) {}

    fun shouldFinishProcessing(recipe: Recipe) = true

    fun finishProcessing(recipe: Recipe) {}
}