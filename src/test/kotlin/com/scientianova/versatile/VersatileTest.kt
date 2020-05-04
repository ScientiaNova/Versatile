package com.scientianova.versatile

import net.alexwells.kottle.FMLKotlinModLoadingContext
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent

@Mod(VersatileTest.MOD_ID)
object VersatileTest {
    const val MOD_ID = "versatiletest"

    init {
        FMLKotlinModLoadingContext.get().modEventBus.addListener<InterModProcessEvent> { processIMC() }
    }

    private fun processIMC() {
        RecipeTest
    }
}