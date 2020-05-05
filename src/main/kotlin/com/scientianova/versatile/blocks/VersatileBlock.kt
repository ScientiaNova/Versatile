package com.scientianova.versatile.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class VersatileBlock(properties: ExtendedBlockProperties) : Block(properties) {
    private val localizedNameFun = properties.localizedNameFun
    private val isAir = properties.isAir
    private val isBurning = properties.isBurning
    private val isFoliage = properties.isFoliage
    private val isBeaconBase = properties.isBeaconBase
    private val isConduitFrame = properties.isConduitFrame
    private val isPortalFrame = properties.isPortalFrame
    private val enchantingBoost = properties.enchantingBoost

    @OnlyIn(Dist.CLIENT)
    override fun getNameTextComponent() = localizedNameFun()
    override fun isAir(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = isAir
    override fun isBurning(state: BlockState?, world: IBlockReader?, pos: BlockPos?) = isBurning
    override fun isFoliage(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = isFoliage
    override fun isBeaconBase(state: BlockState?, world: IWorldReader?, pos: BlockPos?, beacon: BlockPos?) = isBeaconBase
    override fun isConduitFrame(state: BlockState?, world: IWorldReader?, pos: BlockPos?, conduit: BlockPos?) = isConduitFrame
    override fun isPortalFrame(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = isPortalFrame
    override fun getEnchantPowerBonus(state: BlockState?, world: IWorldReader?, pos: BlockPos?) = enchantingBoost
}