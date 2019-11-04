package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import org.openzen.zencode.java.ZenCodeType

//Harvest Tiers contain the stats for breaking blocks
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.HarvestTier")
data class HarvestTier @ZenCodeType.Constructor constructor(
        @ZenCodeType.Field val hardness: Float,
        @ZenCodeType.Field val resistance: Float,
        @ZenCodeType.Field val harvestLevel: Int
)