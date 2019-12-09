package com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers

import com.emosewapixel.pixellib.machines.gui.layout.IGUIComponent
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.grouping.IOType

interface IStackSupplierComponent<T : Any> : IGUIComponent {
    val ioType: IOType
    val type: Class<out T>

    fun getStacks(recipe: Recipe): List<T>

    fun getExtraTooltips(stack: T): List<String>
}