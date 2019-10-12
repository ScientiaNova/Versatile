package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import org.openzen.zencode.java.ZenCodeType

//Fluid Materials are materials meant only for generating fluids of. NYI as forge hasn't sorted fluids out yet
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.FluidMaterial")
open class FluidMaterial @JvmOverloads @ZenCodeType.Constructor constructor(name: String, textureType: String, color: Int, tier: Int = 0) : Material(name, textureType, color, tier) {
    @ZenCodeType.Field
    var temperature = 300
    @ZenCodeType.Field
    var boilingMaterial: FluidMaterial? = null
    @ZenCodeType.Field
    var alpha = 0xFF
    @ZenCodeType.Field
    var pH = 7f
        set(value) {
            field = if (value > 14) 14f
            else if (value < 0) 0f
            else value
        }

    @JvmName("invokeFluid")
    operator fun invoke(builder: FluidMaterial.() -> Unit) = builder(this)
}