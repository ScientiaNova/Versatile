package com.emosewapixel.pixellib.machines.properties.implementations.processing.handlers

import com.emosewapixel.pixellib.machines.properties.implementations.fluids.TEFluidOutputProperty
import com.emosewapixel.pixellib.machines.properties.implementations.processing.IProcessingHandler
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidOutputsComponent
import net.minecraftforge.fluids.FluidStack
import java.util.*

class FluidOutputsProcessingHandler(val property: TEFluidOutputProperty) : IProcessingHandler {
    override fun canStartProcessing(recipe: Recipe): Boolean {
        val recipeOutputs = recipe[FluidOutputsComponent::class.java]?.value ?: return true
        return recipeOutputs.size <= property.value.tanks && recipeOutputs.indices.all {
            recipeOutputs[it].count <= property.value.getTankCapacity(it) - property.value.getFluidInTank(it).amount
        }
    }

    override fun finishProcessing(recipe: Recipe) {
        val recipeOutputs = recipe[FluidOutputsComponent::class.java]?.value ?: return
        recipeOutputs.indices.forEach {
            if (recipeOutputs[it].chance <= Random().nextFloat()) return@forEach
            val stack = property.value.getFluidInTank(it)
            if (stack.isEmpty)
                property.value.setFluidInTank(it, recipeOutputs[it].stacks.firstOrNull()?.copy() ?: FluidStack.EMPTY)
            else
                stack.grow(recipeOutputs[it].count)
        }
    }
}