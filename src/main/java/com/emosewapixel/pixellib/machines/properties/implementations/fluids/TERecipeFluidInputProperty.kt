package com.emosewapixel.pixellib.machines.properties.implementations.fluids

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.fluids.RecipeInputFluidStackHandler
import com.emosewapixel.pixellib.machines.properties.implementations.RecipeProperty

class TERecipeFluidInputProperty(override val value: RecipeInputFluidStackHandler, id: String, te: BaseTileEntity) : TEFluidInputProperty(value, id, te) {
    constructor(tanks: Int, recipeProperty: RecipeProperty, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(
            object : RecipeInputFluidStackHandler(tanks, recipeProperty, capacity) {
                override fun onContentsChanged(index: Int) = te.update()
            }, id, te
    )

    override fun createDefault() = TERecipeFluidInputProperty(RecipeInputFluidStackHandler(value.tanks, value.recipeProperty, value.capacity), id, te)
}