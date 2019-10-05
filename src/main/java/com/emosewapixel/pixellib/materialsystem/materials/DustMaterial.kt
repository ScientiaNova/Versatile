package com.emosewapixel.pixellib.materialsystem.materials

import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import net.minecraft.item.Item

//Dust Materials are the basic solid materials for which at least a dust and block is generated
open class DustMaterial(name: String, textureType: String, color: Int, tier: Int) : Material(name, textureType, color, tier) {
    var processingMultiplier = 1
    var refinedMaterial = this
    var meltingTemperature = 0
    var boilingTemperature = 0
    var unrefinedColor: Int = color

    open val defaultItem: Item?
        get() = MaterialItems[this, MaterialRegistry.DUST]

    @JvmName("invokeDust")
    operator fun invoke(builder: DustMaterial.() -> Unit) = builder(this)

    override fun merge(mat: Material) {
        super.merge(mat)
        if (mat is DustMaterial) {
            if (mat.processingMultiplier > processingMultiplier)
                processingMultiplier = mat.processingMultiplier
            if (mat.refinedMaterial != mat)
                refinedMaterial = mat.refinedMaterial
            if (mat.unrefinedColor != mat.color)
                unrefinedColor = mat.unrefinedColor
            if (mat.meltingTemperature != 0)
                meltingTemperature = mat.meltingTemperature
            if (mat.boilingTemperature != 0)
                boilingTemperature = mat.boilingTemperature
        }
    }
}