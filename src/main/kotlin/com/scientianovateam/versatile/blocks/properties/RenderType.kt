package com.scientianovateam.versatile.blocks.properties

import net.minecraft.client.renderer.RenderType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

val VALID_RENDER_TYPES = arrayListOf("solid", "cutout_mipped", "cutout", "translucent", "translucent_no_crumbling")

fun isValidRenderType(type: String) = type in VALID_RENDER_TYPES

@OnlyIn(Dist.CLIENT)
fun renderTypeFromString(type: String) = when (type) {
    "solid" -> RenderType.getSolid()
    "cutout_mipped" -> RenderType.getCutout()
    "cutout" -> RenderType.getCutoutMipped()
    "translucent" -> RenderType.getTranslucent()
    "translucent_no_crumbling" -> RenderType.getTranslucentNoCrumbling()
    else -> error("Invalid render type: $type")
}