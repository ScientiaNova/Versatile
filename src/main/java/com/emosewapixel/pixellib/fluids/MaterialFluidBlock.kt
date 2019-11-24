package com.emosewapixel.pixellib.fluids

import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.main.ObjectType
import net.minecraft.block.BlockState
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.util.text.translation.LanguageMap
import net.minecraft.world.World

class MaterialFluidBlock(override val mat: Material, override val objType: ObjectType) : FlowingFluidBlock({ MaterialFluids[mat, objType] }, objType.blockProperties(mat)), IMaterialObject {
    init {
        registryName = objType.buildRegistryName(mat)
        MaterialBlocks.addBlock(this)
    }

    override fun getNameTextComponent() = if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey) else objType.localize(mat)
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (!entity.isImmuneToFire) {
            val temp = fluid.attributes.temperature
            if (temp > 350)
                entity.attackEntityFrom(FluidDamageSources.HOT_FLUID, temp / 350f)
        }
        if (mat.pH != 7f)
            if (mat.pH > 7)
                entity.attackEntityFrom(FluidDamageSources.BASE, mat.pH - 7)
            else
                entity.attackEntityFrom(FluidDamageSources.ACID, 7 - mat.pH)
    }
}