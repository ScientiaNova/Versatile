package com.scientianovateam.versatile.machines.gui.layout.components.stacksuppliers

import com.scientianovateam.versatile.machines.gui.layout.IGUIComponent
import com.scientianovateam.versatile.recipes.Recipe
import com.scientianovateam.versatile.recipes.components.grouping.IOType

interface IStackSupplierComponent<T : Any> : IGUIComponent {
    val ioType: IOType
    val type: Class<out T>

    fun getStacks(recipe: Recipe): List<T>

    fun getExtraTooltips(stack: T): List<String>
}