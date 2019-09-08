package com.emosewapixel.pixellib.materialsystem.materials

import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import net.minecraft.item.Item

//Dust Materials are the basic solid materials for which at least a dust and block is generated
open class DustMaterial(name: String, textureType: String, color: Int, tier: Int) : Material(name, textureType, color, tier) {
    var harvestTier: HarvestTier? = null
    var processingMultiplier = 1
    var refinedMaterial: DustMaterial? = null
    var meltingTemperature = 0
    var boilingTemperature = 0
    var unrefinedColor: Int = color

    open val defaultItem: Item?
        get() = MaterialItems.getItem(this, MaterialRegistry.DUST)

    init {
        harvestTier = harvestTier(1.5f * (tier + 1), 1.5f * (tier + 1))
    }

    @JvmName("invokeDust")
    operator fun invoke(builder: DustMaterial.() -> Unit) = builder(this)

    fun harvestTier(hardness: Float, resistance: Float) = HarvestTier(hardness, resistance, tier)

    override fun merge(mat: Material) {
        super.merge(mat)
        if (mat is DustMaterial) {
            if (mat.harvestTier != null)
                harvestTier = mat.harvestTier
            if (mat.processingMultiplier > processingMultiplier)
                processingMultiplier = mat.processingMultiplier
            if (mat.refinedMaterial != null)
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