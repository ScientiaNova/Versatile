package com.scientianova.versatile.machines.properties.implementations.strings

import com.scientianovateam.versatile.common.extensions.nbt
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.packets.UpdateStringPacket
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TEStringProperty(override val id: String, override val te: BaseTileEntity, startingValue: String) : VariableStringProperty(startingValue), ITEBoundProperty {
    override fun setValue(new: String, causeUpdate: Boolean) {
        if (causeUpdate && te.world?.isRemote == true)
            NetworkHandler.CHANNEL.sendToServer(UpdateStringPacket(id, value))
        value = new
        te.markDirty()
    }

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as TEStringProperty).value != value) {
            NetworkHandler.CHANNEL.sendTo(UpdateStringPacket(id, value), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as TEStringProperty).value = value
        }
    }

    override fun deserializeNBT(nbt: CompoundNBT?) {
        value = nbt?.getString(id) ?: ""
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}