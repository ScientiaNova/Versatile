package com.scientianova.versatile.machines.gui.layout.components.stacksuppliers

import com.scientianova.versatile.machines.gui.layout.IGUIComponent
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.components.grouping.IOType

interface IStackSupplierComponent<T : Any> : IGUIComponent {
    val ioType: IOType
    val type: Class<out T>

    fun getStacks(recipe: Recipe): List<T>

    fun getExtraTooltips(stack: T): List<String>
}