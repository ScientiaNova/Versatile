package com.emosewapixel.pixellib.machines.properties.implementations.items

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.items.RecipeInputItemStackHandler
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty

open class TERecipeItemInputProperty(override val value: RecipeInputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInputProperty(value, id, te) {
    constructor(slots: Int, recipeProperty: RecipeProperty, id: String, te: BaseTileEntity) : this(object : RecipeInputItemStackHandler(slots, recipeProperty) {
        override fun onContentsChanged(slot: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone() = TERecipeItemInputProperty(RecipeInputItemStackHandler(value.slots, value.recipeProperty), id, te)
}