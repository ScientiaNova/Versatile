package com.scientianovateam.versatile.machines.defaults

import com.scientianovateam.versatile.blocks.ModBlock
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.IMachineBlock
import com.scientianovateam.versatile.machines.gui.BaseContainerProvider
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.packets.reopening.ChangePagePacket
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.fml.network.NetworkHooks

abstract class AbstractMachineBlock(properties: Properties, name: String) : ModBlock(properties, name), IMachineBlock {
    init {
        BaseTileEntity.USED_BY += this
    }

    override fun onBlockActivated(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): Boolean {
        if (!worldIn.isRemote) {
            val te = worldIn.getTileEntity(pos)!!
            (player as ServerPlayerEntity).connection.sendPacket(worldIn.getTileEntity(pos)!!.updatePacket!!)
            NetworkHandler.CHANNEL.sendTo(ChangePagePacket(te.pos, 0), player.connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            NetworkHooks.openGui(player, BaseContainerProvider(pos), pos)
        }
        return true
    }

    override fun harvestBlock(world: World, player: PlayerEntity, pos: BlockPos, state: BlockState, te: TileEntity?, heldStack: ItemStack) {
        (te as? BaseTileEntity)?.clear()
        super.harvestBlock(world, player, pos, state, te, heldStack)
    }
}