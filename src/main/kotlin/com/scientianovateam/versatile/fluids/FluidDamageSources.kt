package com.scientianovateam.versatile.fluids

import net.minecraft.util.DamageSource

object FluidDamageSources {
    @JvmField
    val ACID = DamageSource("acid")
    @JvmField
    val BASE = DamageSource("base")
    @JvmField
    val HOT_FLUID = DamageSource("hot_fluid").setFireDamage()
}