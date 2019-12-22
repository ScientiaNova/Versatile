package com.scientianovateam.versatile.machines.recipes

import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

object StackToRecipeStackHashConversion {
    private val itemStackList = mutableListOf<(ItemStack) -> List<String>>()
    private val fluidStackList = mutableListOf<(FluidStack) -> List<String>>()

    init {
        itemStackList.add { listOf("item:" + it.item.registryName) }
        itemStackList.add { it.item.tags.map { tag -> "item_tag:$tag" } }

        fluidStackList.add { listOf("fluid:" + it.fluid.registryName) }
        fluidStackList.add { it.fluid.tags.map { tag -> "fluid_tag:$tag" } }
    }

    fun addItemStackConverter(function: (ItemStack) -> List<String>) = itemStackList.add(function)
    fun addFluidStackConverter(function: (FluidStack) -> List<String>) = fluidStackList.add(function)

    fun convertItemStack(stack: ItemStack) = itemStackList.flatMap { it.invoke(stack) }
    fun convertFluidStack(stack: FluidStack) = fluidStackList.flatMap { it.invoke(stack) }
}