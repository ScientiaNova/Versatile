package com.scientianovateam.versatile.items.properties

import net.minecraft.item.Food
import net.minecraft.potion.EffectInstance
import org.apache.commons.lang3.tuple.Pair

class LazyFood(
        value: Int = 0,
        saturation: Float = 0f,
        isMeat: Boolean = false,
        canEatWhenFull: Boolean = false,
        fastToEat: Boolean = false,
        effectSupplier: () -> List<Pair<EffectInstance, Float>> = { emptyList() }
) : Food(value, saturation, isMeat, canEatWhenFull, fastToEat, emptyList()) {
    private val effectList by lazy(effectSupplier)
    override fun getEffects() = effectList
}