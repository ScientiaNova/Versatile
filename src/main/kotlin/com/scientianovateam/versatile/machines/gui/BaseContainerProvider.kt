package com.scientianovateam.versatile.machines.gui

import com.scientianovateam.versatile.machines.BaseTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.StringTextComponent

class BaseContainerProvider(val pos: BlockPos) : INamedContainerProvider {
    override fun createMenu(id: Int, playerInv: PlayerInventory, player: PlayerEntity) =
            BaseContainer(id, playerInv, player.world.getTileEntity(pos) as BaseTileEntity)

    override fun getDisplayName() = StringTextComponent("Base Container")
}