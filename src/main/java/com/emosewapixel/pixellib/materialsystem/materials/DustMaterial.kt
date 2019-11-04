package com.emosewapixel.pixellib.materialsystem.materials

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import org.openzen.zencode.java.ZenCodeType

//Dust Materials are the basic solid materials for which at least a dust and block is generated
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.DustMaterial")
open class DustMaterial(name: String, textureType: String, color: Int, tier: Int) : Material(name, textureType, color, tier) {
    @ZenCodeType.Field
    var processingMultiplier = 1
    @ZenCodeType.Field
    var refinedMaterial = this
    @ZenCodeType.Field
    var meltingTemperature = 0
    @ZenCodeType.Field
    var boilingTemperature = 0
    @ZenCodeType.Field
    var unrefinedColor: Int = color

    open val defaultItem @ZenCodeType.Getter get() = MaterialItems[this, BaseMaterials.DUST]

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