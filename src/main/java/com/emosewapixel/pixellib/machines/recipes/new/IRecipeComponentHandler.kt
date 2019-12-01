package com.emosewapixel.pixellib.machines.recipes.new

interface IRecipeComponentHandler<T> {
    val value: T
    val pairedComponentType: Class<IRecipeComponent<T, *>>
}