package com.scientianovateam.versatile.materialsystem.properties

import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.expr
import com.scientianovateam.versatile.velisp.types.ITypeHolder
import com.scientianovateam.versatile.velisp.unresolved.IUnresolved
import net.minecraft.util.ResourceLocation

data class Property(
        val name: ResourceLocation,
        val type: ITypeHolder,
        val default: IUnresolved,
        val valid: IUnresolved = BoolValue.TRUE
) {
    constructor(
            name: String,
            type: ITypeHolder,
            default: Any,
            valid: Any = BoolValue.TRUE
    ) : this(name.toResLocV(), type, default.expr(), valid.expr())
}