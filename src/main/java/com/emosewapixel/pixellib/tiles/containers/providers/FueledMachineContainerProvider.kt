package com.emosewapixel.pixellib.tiles.containers.providers

import com.emosewapixel.pixellib.tiles.FuelBasedTE
import com.emosewapixel.pixellib.tiles.containers.FuelBasedMachineContainer
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.ContainerType
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos

open class FueledMachineContainerProvider(pos: BlockPos?, name: ResourceLocation, containerType: ContainerType<*>) : MachineContainerProvider(pos, name, containerType) {
    override fun createMenu(id: Int, playerInventory: PlayerInventory, playerEntity: PlayerEntity) = FuelBasedMachineContainer(playerInventory, (playerEntity.world.getTileEntity(pos!!) as FuelBasedTE?)!!, containerType, id)
}