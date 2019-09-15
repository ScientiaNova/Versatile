package com.emosewapixel.pixellib.materialsystem.materials

//Fluid Materials are materials meant only for generating fluids of. NYI as forge hasn't sorted fluids out yet
open class FluidMaterial @JvmOverloads constructor(name: String, textureType: String, color: Int, tier: Int = 0) : Material(name, textureType, color, tier) {
    var temperature = 300
    var boilingMaterial: FluidMaterial? = null
    var pH = 7f
        set(value) {
            field = if (value > 14) 14f
            else if (value < 0) 0f
            else value
        }

    @JvmName("invokeFluid")
    operator fun invoke(builder: FluidMaterial.() -> Unit) = builder(this)
}