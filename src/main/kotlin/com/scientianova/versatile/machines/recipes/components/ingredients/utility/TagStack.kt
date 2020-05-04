package com.scientianova.versatile.machines.recipes.components.ingredients.utility

import com.scientianova.versatile.common.extensions.times
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.tags.Tag

//Tag Stacks are a way of getting an amount of a certain Item Tag. They're meant to be used in Machine Recipes
data class TagStack<T> @JvmOverloads constructor(var tag: Tag<T>, var count: Int = 1) {
    val isEmpty get() = count == 0
}

fun TagStack<Item>.asItemStack() = (tag.allElements.firstOrNull() ?: Items.AIR) * count
fun TagStack<Fluid>.asFluidStack() = (tag.allElements.firstOrNull() ?: Fluids.EMPTY) * count

fun <T> Tag<T>.toStack(count: Int = 1) = TagStack(this, count)
operator fun <T> Tag<T>.times(count: Int) = TagStack(this, count)