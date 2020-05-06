package com.scientianova.versatile.machines.defaults

import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.IMachineBlock
import com.scientianova.versatile.machines.gui.BaseContainerProvider
import com.scientianova.versatile.machines.packets.NetworkHandler
import com.scientianova.versatile.machines.packets.reopening.ChangePagePacket
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkHooks

abstract class AbstractMachineBlock(properties: Properties) : Block(properties), IMachineBlock {
    init {
        BaseTileEntity.USED_BY += this
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!worldIn.isRemote) {
            val te = worldIn.getTileEntity(pos)!!
            (player as ServerPlayerEntity).connection.sendPacket(worldIn.getTileEntity(pos)!!.updatePacket!!)
            NetworkHandler.CHANNEL.sendTo(ChangePagePacket(te.pos, 0), player.connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            NetworkHooks.openGui(player, BaseContainerProvider(pos), pos)
        }
        return ActionResultType.SUCCESS
    }

    override fun harvestBlock(world: World, player: PlayerEntity, pos: BlockPos, state: BlockState, te: TileEntity?, heldStack: ItemStack) {
        (te as? BaseTileEntity)?.clear()
        super.harvestBlock(world, player, pos, state, te, heldStack)
    }
}