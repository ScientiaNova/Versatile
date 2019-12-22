package com.scientianovateam.versatile.machines.recipes.components

interface IRecipeComponentHandler<T> {
    val value: T
    val pairedComponentType: Class<out IRecipeComponent<T>>
    val serializer: IRecipeHandlerSerializer<out IRecipeComponentHandler<T>>
}