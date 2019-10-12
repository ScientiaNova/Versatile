package com.emosewapixel.pixellib.materialsystem.materials.utility

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.materials.Material
import com.emosewapixel.pixellib.materialsystem.materials.utility.ct.MaterialSupplier
import org.openzen.zencode.java.ZenCodeType

@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.TransitionMaterial")
open class TransitionMaterial constructor(name: String, private val endMaterialFun: MaterialSupplier, @ZenCodeType.Field val neededAmount: Int) : Material(name, "", -1, -1) {
    val endMaterial: Material
        @ZenCodeType.Getter get() = endMaterialFun.get()

    @JvmName("invokeTransition")
    operator fun invoke(builder: TransitionMaterial.() -> Unit) = builder(this)
}