package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.getOrAddInstance
import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.FluidCapabilityWrapper
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateTankPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.google.common.reflect.MutableTypeToInstanceMap
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.fml.network.NetworkDirection

open class TEFluidInventoryProperty(value: FluidStackHandler, override val id: String, override val te: BaseTileEntity) : FluidInventoryProperty(value), ITEBoundProperty {
    constructor(te: BaseTileEntity, id: String, slots: Int, noOutput: IntRange, noInput: IntRange, capacity: Int = 10000) : this(
            object : FluidStackHandler(slots, noOutput, noInput, capacity) {
                override fun onContentsChanged(index: Int) = te.update()
            }, id, te
    )

    constructor(te: BaseTileEntity, id: String, inputCount: Int, outputCount: Int, capacity: Int = 10000) : this(
            te, id, inputCount + outputCount, 0 until inputCount, inputCount until inputCount + outputCount, capacity
    )

    override fun detectAndSendChanges(container: BaseContainer) {
        val clientHandler = (container.clientProperties[id] as FluidInventoryProperty).value
        clientHandler.tanks.indices.forEach { index ->
            if (clientHandler.tanks[index] != value.tanks[index]) {
                NetworkHandler.CHANNEL.sendTo(UpdateTankPacket(id, index, value.tanks[index]), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
                clientHandler.tanks[index] = value.tanks[index].copy()
            }
        }
    }

    override fun createDefault() = TEFluidInventoryProperty(FluidStackHandler(value.count, value.noOutputTanks, value.noInputTanks, value.capacity), id, te)

    override fun deserializeNBT(nbt: CompoundNBT) {
        if (id in nbt) value.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to value
    }

    override fun addCapability(map: MutableTypeToInstanceMap<ICapabilityProvider>) {
        map.getOrAddInstance(FluidCapabilityWrapper::class.java, FluidCapabilityWrapper()).addHandler(value)
    }
}