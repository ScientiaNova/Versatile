package com.emosewapixel.pixellib.machines.recipes

interface IRecipeComponentHandler<T> {
    val value: T
    val pairedComponentType: Class<IRecipeComponent<T, *>>
}