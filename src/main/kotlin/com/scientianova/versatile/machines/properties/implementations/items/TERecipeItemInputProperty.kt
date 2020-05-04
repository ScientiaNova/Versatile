package com.scientianova.versatile.machines.properties.implementations.items

import com.scientianovateam.versatile.common.extensions.get
import com.scientianovateam.versatile.common.extensions.set
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.capabilities.items.RecipeInputItemStackHandler
import com.scientianovateam.versatile.machines.properties.implementations.recipes.RecipeProperty

open class TERecipeItemInputProperty(override val value: RecipeInputItemStackHandler, id: String, te: BaseTileEntity) : TEItemInputProperty(value, id, te) {
    constructor(slots: Int, recipeProperty: RecipeProperty, id: String, te: BaseTileEntity) : this(object : RecipeInputItemStackHandler(slots, recipeProperty) {
        override fun onContentsChanged(slot: Int) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun clone(): TERecipeItemInputProperty {
        val stackHandler = RecipeInputItemStackHandler(value.slots, value.recipeProperty)
        (0 until value.slots).forEach { stackHandler[it] = value[it].copy() }
        return TERecipeItemInputProperty(stackHandler, id, te)
    }
}