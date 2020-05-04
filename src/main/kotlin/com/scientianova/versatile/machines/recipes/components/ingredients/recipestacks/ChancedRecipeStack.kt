package com.scientianova.versatile.machines.recipes.components.ingredients.recipestacks

open class ChancedRecipeStack<T>(val value: IRecipeStack<T>, val chance: Float = 1f) : IRecipeStack<T> by value

fun <T> IRecipeStack<T>.chanced(chance: Float = 1f) = ChancedRecipeStack(this, chance)