package com.emosewapixel.pixellib.machines.gui

import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.layout.IPropertyGUIComponent
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType

class BaseContainer(id: Int, val playerInv: PlayerInventory, val te: BaseTileEntity, type: ContainerType<*>) : Container(type, id) {
    val guiPage = te.guiLayout.current
    val clientProperties = te.properties.mapValues { it.value.copy() }

    override fun canInteractWith(playerIn: PlayerEntity) = te.canInteractWith(playerIn)

    override fun detectAndSendChanges() = guiPage.components.asSequence().filterIsInstance<IPropertyGUIComponent>().distinct().forEach { (it.property as? ITEBoundProperty)?.detectAndSendChanges(this) }
}