package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.ImprovedItemStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateNBTSerializableProperty
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.PacketDistributor

open class TEItemInventoryProperty(value: ImprovedItemStackHandler, override val id: String, override val te: BaseTileEntity) : ItemInventoryProperty(value), ITEBoundProperty {
    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as? TEItemInventoryProperty)?.value?.let {
                    it.stacks.indices.any { index -> it.stacks[index] == value.stacks[index] }
                } == false
        ) {
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as? ServerPlayerEntity }, UpdateNBTSerializableProperty(te.pos, id, value.serializeNBT()))
            (container.clientProperties[id] as TEItemInventoryProperty).value.stacks = value.stacks
        }
    }

    override fun copy() = TEItemInventoryProperty(value, id, te)

    override fun deserializeNBT(nbt: CompoundNBT) {
        value.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}