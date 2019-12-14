package com.emosewapixel.pixellib.machines.properties.implementations.primitives.doubles

import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.recipes.components.stats.TimeComponent

open class RecipeTimeBasedDoubleProperty(val intProperty: IValueProperty<Int>, val recipeProperty: RecipeProperty) : IValueProperty<Double> {
    override val value: Double
        get() {
            val recipeTime = recipeProperty.value?.get(TimeComponent)?.value ?: return 0.0
            return intProperty.value / recipeTime.toDouble()
        }

    override fun createDefault() = RecipeTimeBasedDoubleProperty(intProperty, recipeProperty)
}