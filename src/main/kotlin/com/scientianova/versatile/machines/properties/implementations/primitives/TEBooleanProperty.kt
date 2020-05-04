package com.scientianova.versatile.machines.properties.implementations.primitives

import com.scientianovateam.versatile.common.extensions.nbt
import com.scientianovateam.versatile.machines.BaseTileEntity
import com.scientianovateam.versatile.machines.gui.BaseContainer
import com.scientianovateam.versatile.machines.packets.NetworkHandler
import com.scientianovateam.versatile.machines.packets.primitives.UpdateBooleanPacket
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import com.scientianovateam.versatile.machines.properties.IVariableProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TEBooleanProperty(override val id: String, override val te: BaseTileEntity) : IVariableProperty<Boolean>, ITEBoundProperty {
    override fun setValue(new: Boolean, causeUpdate: Boolean) {
        if (causeUpdate && te.world?.isRemote == true)
            NetworkHandler.CHANNEL.sendToServer(UpdateBooleanPacket(id, value))
        value = new
        te.markDirty()
    }

    override var value = false
        protected set

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as TEBooleanProperty).value != value) {
            NetworkHandler.CHANNEL.sendTo(UpdateBooleanPacket(id, value), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as TEBooleanProperty).value = value
        }
    }

    override fun clone() = TEBooleanProperty(id, te)

    override fun deserializeNBT(nbt: CompoundNBT?) {
        if (nbt?.contains(id) == true) value = nbt.getBoolean(id)
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}