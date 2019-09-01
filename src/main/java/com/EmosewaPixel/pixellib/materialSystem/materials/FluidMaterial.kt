package com.EmosewaPixel.pixellib.materialsystem.materials

import com.EmosewaPixel.pixellib.materialsystem.types.TextureType

//Fluid Materials are materials meant only for generating fluids of. NYI as forge hasn't sorted fluids out yet
open class FluidMaterial @JvmOverloads constructor(name: String, textureType: TextureType?, color: Int, tier: Int = 0) : Material(name, textureType, color, tier) {
    var temperature = 373
    var boilingMaterial: FluidMaterial? = null

    @JvmName("invokeFluid")
    operator fun invoke(builder: FluidMaterial.() -> Unit) = builder(this)
}