package com.scientianovateam.versatile.materialsystem.properties

import com.scientianovateam.versatile.velisp.VELISPType
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.util.ResourceLocation

data class Property(
        val name: String,
        val type: VELISPType,
        val default: IUnresolved,
        val valid: IUnresolved
)