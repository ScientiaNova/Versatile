package com.scientianovateam.versatile.machines.properties.implementations.processing.handlers

import com.scientianovateam.versatile.machines.properties.implementations.fluids.TEFluidOutputProperty
import com.scientianovateam.versatile.machines.properties.implementations.processing.IProcessingHandler
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.ingredients.fluids.output.FluidOutputsComponent
import net.minecraftforge.fluids.capability.IFluidHandler
import java.util.*

class FluidOutputsProcessingHandler(val property: TEFluidOutputProperty) : IProcessingHandler {
    override fun canStartProcessingStandard(recipe: Recipe): Boolean {
        val recipeOutputs = recipe[FluidOutputsComponent::class.java]?.value ?: return true
        val handlerClone = property.clone().value
        return recipeOutputs.all {
            val stack = it.singleStack
            handlerClone.forceFill(stack, IFluidHandler.FluidAction.EXECUTE) == stack.amount
        }
    }

    override fun canStartProcessingAutomation(recipe: Recipe): Boolean {
        val recipeOutputs = recipe[FluidOutputsComponent::class.java]?.value ?: return true
        return recipeOutputs.size <= property.value.tanks && recipeOutputs.indices.all {
            recipeOutputs[it].count <= property.value.getTankCapacity(it) - property.value.getFluidInTank(it).amount
        }
    }

    override fun finishProcessingStandard(recipe: Recipe) {
        val recipeOutputs = recipe[FluidOutputsComponent::class.java]?.value ?: return
        recipeOutputs.forEach { property.value.forceFill(it.singleStack, IFluidHandler.FluidAction.EXECUTE) }
    }

    override fun finishProcessingAutomation(recipe: Recipe) {
        val recipeOutputs = recipe[FluidOutputsComponent::class.java]?.value ?: return
        recipeOutputs.indices.forEach {
            if (recipeOutputs[it].chance <= Random().nextFloat()) return@forEach
            val stack = property.value.getFluidInTank(it)
            if (stack.isEmpty)
                property.value.setFluidInTank(it, recipeOutputs[it].singleStack)
            else
                stack.grow(recipeOutputs[it].count)
        }
    }
}