package com.emosewapixel.pixellib.fluids

import net.minecraft.util.DamageSource

object FluidDamageSources {
    @JvmField
    val ACID = DamageSource("acid")
    @JvmField
    val HOT_FLUID = DamageSource("hot_fluid").setFireDamage()
}