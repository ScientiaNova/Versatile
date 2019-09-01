package com.EmosewaPixel.pixellib.tiles.containers.providers

import com.EmosewaPixel.pixellib.tiles.RecipeBasedTE
import com.EmosewaPixel.pixellib.tiles.containers.RecipeBasedMachineContainer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ContainerType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos

open class MachineContainerProvider(pos: BlockPos?, name: ResourceLocation, containerType: ContainerType<*>) : AbstractMachineContainerProvider(pos, name, containerType) {
    override fun createMenu(id: Int, playerInventory: PlayerInventory, playerEntity: PlayerEntity) = RecipeBasedMachineContainer(playerInventory, (playerEntity.world.getTileEntity(pos!!) as RecipeBasedTE?)!!, containerType, id)
}