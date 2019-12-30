package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.serialization.IRegisterableSerializer
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.Ingredient

interface IRecipeStack<T> {
    val count: Int

    val stacks: List<T>

    val serializer: IRegisterableSerializer<out IRecipeStack<T>, JsonObject>

    fun matches(other: T): Boolean

    fun matchesWithoutCount(other: T): Boolean

    override fun toString(): String
}

fun IRecipeStack<ItemStack>.toIngredient() = Ingredient.fromStacks(*stacks.toTypedArray())

@Suppress("UNCHECKED_CAST")
fun <T : IRecipeStack<*>> T.serialize() = (serializer as IRegisterableSerializer<T, JsonObject>).write(this)