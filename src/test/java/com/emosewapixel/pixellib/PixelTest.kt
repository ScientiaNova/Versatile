package com.emosewapixel.pixellib

import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

@Mod(PixelTest.MOD_ID)
object PixelTest {
    const val MOD_ID = "pixeltest"

    init {
        FMLKotlinModLoadingContext.get().modEventBus.addListener<InterModProcessEvent> { processIMC() }
    }

    private fun processIMC() {
        RecipeTest
    }
}