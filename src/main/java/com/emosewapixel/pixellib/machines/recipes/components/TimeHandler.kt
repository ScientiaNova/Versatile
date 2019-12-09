package com.emosewapixel.pixellib.machines.recipes.components

class TimeHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = TimeComponent::class.java
}