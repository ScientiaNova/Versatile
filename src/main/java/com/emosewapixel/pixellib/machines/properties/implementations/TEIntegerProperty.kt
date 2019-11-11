package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateIntPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.network.PacketDistributor

class TEIntegerProperty(override val id: String, override val te: BaseTileEntity) : IVariableProperty<Int>, ITEBoundProperty {
    override fun setValue(new: Int, causeUpdate: Boolean) {
        if (causeUpdate && FMLEnvironment.dist.isClient)
            NetworkHandler.CHANNEL.sendToServer(UpdateIntPacket(te.pos, id, value))
        value = new
    }

    override var value = 0
        private set

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as TEIntegerProperty).value != value) {
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with { container.playerInv.player as? ServerPlayerEntity }, UpdateIntPacket(te.pos, id, value))
            (container.clientProperties[id] as TEIntegerProperty).value = value
        }
    }

    override fun copy() = TEIntegerProperty(id, te)

    override fun deserializeNBT(nbt: CompoundNBT?) {
        value = nbt?.getInt(id) ?: 0
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}