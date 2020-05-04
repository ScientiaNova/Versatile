package com.scientianova.versatile.fluids

import com.scientianova.versatile.materialsystem.lists.MaterialBlocks
import com.scientianova.versatile.materialsystem.lists.MaterialFluids
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.ObjectType
import net.minecraft.block.BlockState
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.World

class MaterialFluidBlock(override val mat: Material, override val objType: ObjectType) : FlowingFluidBlock({ MaterialFluids[mat, objType] }, objType.blockProperties(mat)), IMaterialObject {
    init {
        registryName = objType.registryName(mat)
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