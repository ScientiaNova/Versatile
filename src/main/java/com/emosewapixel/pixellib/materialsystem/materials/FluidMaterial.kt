package com.emosewapixel.pixellib.materialsystem.materials

//Fluid Materials are materials meant only for generating fluids of. NYI as forge hasn't sorted fluids out yet
open class FluidMaterial @JvmOverloads constructor(name: String, textureType: String, color: Int, tier: Int = 0) : Material(name, textureType, color, tier) {
    var temperature = 373
    var boilingMaterial: FluidMaterial? = null

    @JvmName("invokeFluid")
    operator fun invoke(builder: FluidMaterial.() -> Unit) = builder(this)
}