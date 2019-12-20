package com.emosewapixel.pixellib.machines.properties.implementations.fluids

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.fluids.RecipeInputFluidStackHandler
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty

class TERecipeFluidInputProperty(override val value: RecipeInputFluidStackHandler, id: String, te: BaseTileEntity) : TEFluidInputProperty(value, id, te) {
    constructor(tanks: Int, recipeProperty: RecipeProperty, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(
            object : RecipeInputFluidStackHandler(tanks, recipeProperty, capacity) {
                override fun onContentsChanged(indices: List<Int>) {
                    te.markDirty()
                    te.update()
                }
            }, id, te
    )

    override fun clone(): TERecipeFluidInputProperty {
        val stackHandler = RecipeInputFluidStackHandler(value.count, value.recipeProperty, value.capacity)
        (0 until value.tanks).forEach { stackHandler[it] = value[it].copy() }
        return TERecipeFluidInputProperty(stackHandler, id, te)
    }
}