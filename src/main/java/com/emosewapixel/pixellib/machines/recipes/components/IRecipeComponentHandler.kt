package com.emosewapixel.pixellib.machines.recipes.components

interface IRecipeComponentHandler<T> {
    val value: T
    val pairedComponentType: Class<out IRecipeComponent<T>>
}