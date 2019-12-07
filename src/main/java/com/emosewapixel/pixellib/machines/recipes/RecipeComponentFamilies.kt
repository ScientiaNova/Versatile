package com.emosewapixel.pixellib.machines.recipes

object RecipeComponentFamilies {
    @JvmField
    val INPUT_SLOTS = RecipeComponentFamily("slots", IOType.INPUT)

    @JvmField
    val PATTERN_SLOTS = RecipeComponentFamily("pattern_slot", IOType.INPUT)

    @JvmField
    val PATTERN_BUTTONS = RecipeComponentFamily("pattern_buttons", IOType.INPUT)

    @JvmField
    val OUTPUT_SLOTS = RecipeComponentFamily("slots", IOType.OUTPUT)
}