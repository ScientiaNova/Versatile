package com.EmosewaPixel.pixellib.tiles.containers.providers

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.inventory.container.INamedContainerProvider
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TranslationTextComponent

abstract class AbstractMachineContainerProvider(protected var pos: BlockPos?, private val name: ResourceLocation, protected var containerType: ContainerType<*>) : INamedContainerProvider {
    abstract override fun createMenu(id: Int, playerInventory: PlayerInventory, playerEntity: PlayerEntity): Container?

    override fun getDisplayName() = TranslationTextComponent("block." + name.namespace + "." + name.path, *arrayOfNulls(0))
}