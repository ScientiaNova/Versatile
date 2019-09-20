package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.BaseTileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType

class BaseContainer(type: ContainerType<*>, val te: BaseTileEntity, id: Int) : Container(type, id) {
    override fun canInteractWith(playerIn: PlayerEntity) = te.canInteractWith(playerIn)
}