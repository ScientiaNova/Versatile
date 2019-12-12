package com.emosewapixel.pixellib.machines.properties.implementations.energy

import com.emosewapixel.pixellib.extensions.getOrAddInstance
import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.energy.EnergyCapabilityWrapper
import com.emosewapixel.pixellib.machines.capabilities.energy.EnergyHandler
import com.emosewapixel.pixellib.machines.capabilities.energy.IEnergyStorageModifiable
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.primitives.UpdateIntPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.google.common.reflect.MutableTypeToInstanceMap
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.fml.network.NetworkDirection

open class TEEnergyProperty(override val value: EnergyHandler, override val id: String, override val te: BaseTileEntity) : IValueProperty<IEnergyStorageModifiable>, ITEBoundProperty {
    constructor(capacity: Int, id: String, te: BaseTileEntity) : this(object : EnergyHandler(capacity) {
        override fun onUpdate() {
            te.update()
            te.markDirty()
        }
    }, id, te)

    override fun createDefault() = TEEnergyProperty(value.maxEnergyStored, id, te)

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as TEEnergyProperty).value.energyStored != value.energyStored) {
            NetworkHandler.CHANNEL.sendTo(UpdateIntPacket(id, value.energyStored), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as TEEnergyProperty).value.energyStored = value.energyStored
        }
    }

    override fun deserializeNBT(nbt: CompoundNBT?) = value.deserializeNBT(nbt?.getCompound(id))

    override fun serializeNBT() = nbt {
        id to value.serializeNBT()
    }

    override fun addCapability(map: MutableTypeToInstanceMap<ICapabilityProvider>) {
        map.getOrAddInstance(EnergyCapabilityWrapper::class.java, EnergyCapabilityWrapper()).addHandler(value)
    }
}