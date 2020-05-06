package com.scientianova.versatile.fluids

import com.scientianova.versatile.blocks.ExtendedBlockProperties
import net.minecraft.block.BlockState
import net.minecraft.block.FlowingFluidBlock
import net.minecraft.entity.Entity
import net.minecraft.fluid.FlowingFluid
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.LanguageMap
import net.minecraft.util.text.TranslationTextComponent
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fluids.FluidStack

class VersatileFluidBlock(fluid: () -> FlowingFluid, properties: ExtendedBlockProperties) : FlowingFluidBlock(fluid, properties) {
    private val isAir = properties.isAir
    private val isBurning = properties.isBurning
    private val isFoliage = properties.isFoliage
    private val isBeaconBase = properties.isBeaconBase
    private val isConduitFrame = properties.isConduitFrame
    private val isPortalFrame = properties.isPortalFrame
    private val enchantingBoost = properties.enchantingBoost

    override fun isAir(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = isAir
    override fun isBurning(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = isBurning
    override fun isFoliage(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = isFoliage
    override fun isBeaconBase(state: BlockState?, world: IWorldReader?, pos: BlockPos?, beacon: BlockPos?) = isBeaconBase
    override fun isConduitFrame(state: BlockState?, world: IWorldReader?, pos: BlockPos?, conduit: BlockPos?) = isConduitFrame
    override fun isPortalFrame(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = isPortalFrame
    override fun getEnchantPowerBonus(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = enchantingBoost

    @OnlyIn(Dist.CLIENT)
    override fun getNameTextComponent(): ITextComponent =
            if (LanguageMap.getInstance().exists(translationKey)) TranslationTextComponent(translationKey)
            else fluid.attributes.getDisplayName(FluidStack.EMPTY)

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (!entity.isImmuneToFire) {
            val temp = fluid.attributes.temperature
            if (temp > 350)
                entity.attackEntityFrom(HOT_FLUID_DAMAGE, temp / 350f)
        }
    }
}