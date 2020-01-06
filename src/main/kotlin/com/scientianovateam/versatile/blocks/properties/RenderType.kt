package com.scientianovateam.versatile.blocks.properties

import net.minecraft.client.renderer.RenderType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

val VALID_RENDER_TYPES = arrayListOf("solid", "cutout_mipped", "cutout", "translucent", "translucent_no_crumbling")

fun isValidRenderType(type: String) = type in VALID_RENDER_TYPES

@OnlyIn(Dist.CLIENT)
fun renderTypeFromString(type: String) = when (type) {
    "solid" -> RenderType.func_228639_c_()
    "cutout_mipped" -> RenderType.func_228641_d_()
    "cutout" -> RenderType.func_228643_e_()
    "translucent" -> RenderType.func_228645_f_()
    "translucent_no_crumbling" -> RenderType.func_228647_g_()
    else -> error("Invalid render type: $type")
}