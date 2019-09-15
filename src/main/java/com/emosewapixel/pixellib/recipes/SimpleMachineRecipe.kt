package com.emosewapixel.pixellib.recipes

import net.minecraft.item.ItemStack
import net.minecraftforge.fluids.FluidStack

//Simple Machine Recipes are the most basic Machine Recipes, only having inputs, outputs and a processing time
open class SimpleMachineRecipe(val inputs: Array<Any>, val consumeChances: Array<Float>, val fluidInputs: Array<FluidStack>, val outputs: Array<Any>, val outputChances: Array<Float>, val fluidOutputs: Array<FluidStack>, var time: Int) {
    val outputStacks: List<ItemStack>
        get() = outputs.map { if (it is ItemStack) it else (it as TagStack).asItemStack() }

    val inputsAsLists: List<List<ItemStack>>
        get() = inputs.map { o ->
            if (o is ItemStack)
                listOf(o)
            val (tag, count) = o as TagStack
            tag.allElements.map { i -> ItemStack(i, count) }
        }

    open val isEmpty: Boolean
        get() = this === EMPTY

    fun getInput(index: Int) = inputs[index]

    fun getOutput(index: Int) = when (outputs[index]) {
        is ItemStack -> outputs[index] as ItemStack
        is TagStack -> (outputs[index] as TagStack).asItemStack()
        else -> null
    }

    fun getConsumeChance(index: Int) = consumeChances[index]

    fun getOutputChance(index: Int) = outputChances[index]

    fun isInputValid(stacks: Array<ItemStack>) =
            if (stacks.size != inputs.size || stacks.any { it.isEmpty } || inputs.any { if (it is ItemStack) it.isEmpty else (it as TagStack).isEmpty }) false
            else stacks.size == inputs.map { stackRec ->
                stacks.first { stack -> if (stackRec is ItemStack) stack.isItemEqual(stackRec) && stack.count >= stackRec.count else (stackRec as TagStack).tag.contains(stack.item) && stack.count >= stackRec.count }
                        ?: ItemStack.EMPTY
            }
                    .filter { !it.isEmpty }
                    .count()

    fun getInputCount(index: Int): Int {
        val o = inputs[index]
        return when (o) {
            is ItemStack -> o.count
            is TagStack -> o.count
            else -> 0
        }
    }

    fun getIndexOfInput(stack: ItemStack) =
            listOf(*inputs).indexOf(inputs.first { input -> if (input is ItemStack) stack.isItemEqual(input) else (input as TagStack).tag.contains(stack.item) }
                    ?: -1)

    fun itemBelongsInRecipe(stack: ItemStack) =
            if (stack.isEmpty) false
            else inputs.any {
                if (it is ItemStack)
                    it.isItemEqual(stack)
                else
                    (it as TagStack).tag.contains(stack.item)
            }

    companion object {
        @JvmField
        val EMPTY = SimpleMachineRecipe(arrayOf(), arrayOf(), arrayOf(), arrayOf(), arrayOf(), arrayOf(), 0)
    }
}
