package com.emosewapixel.pixellib.machines.recipes.new

class Recipe(vararg componentHandlers: IRecipeComponentHandler<*>) {
    private val map = componentHandlers.map { it.pairedComponentType to it }.toMap()

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(component: IRecipeComponent<T, *>) = map[component.javaClass] as? IRecipeComponentHandler<T>
}