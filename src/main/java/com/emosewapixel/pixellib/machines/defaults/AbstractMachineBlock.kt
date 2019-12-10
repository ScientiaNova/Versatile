package com.emosewapixel.pixellib.machines.defaults

import com.emosewapixel.pixellib.blocks.ModBlock
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.IMachineBlock
import com.emosewapixel.pixellib.machines.gui.BaseContainerProvider
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks

abstract class AbstractMachineBlock(properties: Properties, name: String) : ModBlock(properties, name), IMachineBlock {
    init {
        BaseTileEntity.USED_BY += this
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): Boolean {
        if (!worldIn.isRemote)
            NetworkHooks.openGui(player as? ServerPlayerEntity, BaseContainerProvider(pos), pos)
        return true
    }
}