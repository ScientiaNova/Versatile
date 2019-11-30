package com.emosewapixel.pixellib.machines.recipes.new

interface IRecipeComponentHandler<T, C: IRecipeComponent<T>> {
    val name: String
}