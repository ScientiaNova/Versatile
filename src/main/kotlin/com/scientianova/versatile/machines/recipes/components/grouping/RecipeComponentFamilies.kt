package com.scientianova.versatile.machines.recipes.components.grouping

object RecipeComponentFamilies {
    @JvmField
    val INPUT_SLOTS = RecipeComponentFamily("slots", IOType.INPUT)

    @JvmField
    val PATTERN_SLOTS = RecipeComponentFamily("pattern_slot", IOType.INPUT)

    @JvmField
    val PATTERN_BUTTONS = RecipeComponentFamily("pattern_buttons", IOType.INPUT)

    @JvmField
    val OUTPUT_SLOTS = RecipeComponentFamily("slots", IOType.OUTPUT)

    @JvmStatic
    val STATS = RecipeComponentFamily("stats", IOType.NONE)
}