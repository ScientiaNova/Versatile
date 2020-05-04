package com.scientianova.versatile.machines.recipes.components

interface IRecipeComponentHandler<T> {
    val value: T
    val pairedComponentType: Class<out IRecipeComponent<T>>
}