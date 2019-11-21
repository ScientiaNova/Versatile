package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateBooleanPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IVariableProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.fml.network.NetworkDirection

open class TEBooleanProperty(override val id: String, override val te: BaseTileEntity) : IVariableProperty<Boolean>, ITEBoundProperty {
    override fun setValue(new: Boolean, causeUpdate: Boolean) {
        if (causeUpdate && !FMLEnvironment.dist.isDedicatedServer)
            NetworkHandler.CHANNEL.sendToServer(UpdateBooleanPacket(id, value))
        value = new
    }

    override var value = false
        protected set

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as TEBooleanProperty).value != value) {
            NetworkHandler.CHANNEL.sendTo(UpdateBooleanPacket(id, value), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as TEBooleanProperty).value = value
        }
    }

    override fun createDefault() = TEBooleanProperty(id, te)

    override fun deserializeNBT(nbt: CompoundNBT?) {
        if (nbt?.contains(id) == true) value = nbt.getBoolean(id)
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}