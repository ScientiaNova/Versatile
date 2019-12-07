package com.emosewapixel.pixellib.machines.properties.implementations.inventory

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.getOrAddInstance
import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.items.ItemCapabilityWrapper
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateSlotPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.google.common.reflect.MutableTypeToInstanceMap
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.fml.network.NetworkDirection
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

open class TEItemInventoryProperty(override val value: ItemStackHandler, override val id: String, override val te: BaseTileEntity) : IValueProperty<IItemHandlerModifiable>, ITEBoundProperty {
    constructor(slots: Int, id: String, te: BaseTileEntity) : this(object : ItemStackHandler(slots) {
        override fun onContentsChanged(slot: Int) = te.update()
    }, id, te)

    override fun detectAndSendChanges(container: BaseContainer) {
        val clientHandler = (container.clientProperties[id] as TEItemInventoryProperty).value
        (0 until clientHandler.slots).forEach { index ->
            if (!clientHandler[index].equals(value[index], false)) {
                NetworkHandler.CHANNEL.sendTo(UpdateSlotPacket(id, index, value[index]), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
                clientHandler[index] = value[index].copy()
            }
        }
    }

    override fun createDefault() = TEItemInventoryProperty(ItemStackHandler(value.slots), id, te)

    override fun deserializeNBT(nbt: CompoundNBT?) {
        if (nbt?.contains(id) == true) value.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to value
    }

    override fun addCapability(map: MutableTypeToInstanceMap<ICapabilityProvider>) {
        map.getOrAddInstance(ItemCapabilityWrapper::class.java, ItemCapabilityWrapper()).addHandler(value)
    }
}