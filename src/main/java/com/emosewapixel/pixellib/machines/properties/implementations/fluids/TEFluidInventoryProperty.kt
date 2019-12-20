package com.emosewapixel.pixellib.machines.properties.implementations.fluids

import com.emosewapixel.pixellib.extensions.get
import com.emosewapixel.pixellib.extensions.getOrAddInstance
import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.extensions.set
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.fluids.FluidCapabilityWrapper
import com.emosewapixel.pixellib.machines.capabilities.fluids.FluidStackHandler
import com.emosewapixel.pixellib.machines.capabilities.fluids.IFluidHandlerModifiable
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.handlers.UpdateTankPacket
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import com.google.common.reflect.MutableTypeToInstanceMap
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.fluids.FluidStack
import net.minecraftforge.fml.network.NetworkDirection

open class TEFluidInventoryProperty(override val value: FluidStackHandler, override val id: String, override val te: BaseTileEntity) : IValueProperty<IFluidHandlerModifiable>, ITEBoundProperty {
    constructor(slots: Int, id: String, te: BaseTileEntity, capacity: Int = 10_000) : this(object : FluidStackHandler(slots, capacity) {
        override fun onContentsChanged(indices: List<Int>) {
            te.markDirty()
            te.update()
        }
    }, id, te)

    override fun detectAndSendChanges(container: BaseContainer) {
        val clientHandler = (container.clientProperties[id] as TEFluidInventoryProperty).value
        (0 until clientHandler.tanks).forEach { index ->
            if (!clientHandler[index].isFluidStackIdentical(value[index])) {
                NetworkHandler.CHANNEL.sendTo(UpdateTankPacket(id, index, value[index]), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
                clientHandler[index] = value[index].copy()
            }
        }
    }

    override fun clone(): TEFluidInventoryProperty {
        val stackHandler = FluidStackHandler(value.count, value.capacity)
        (0 until value.tanks).forEach { stackHandler[it] = value[it].copy() }
        return TEFluidInventoryProperty(stackHandler, id, te)
    }

    override fun deserializeNBT(nbt: CompoundNBT) {
        if (id in nbt) value.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to value
    }

    override fun addCapability(map: MutableTypeToInstanceMap<ICapabilityProvider>) {
        map.getOrAddInstance(FluidCapabilityWrapper::class.java, FluidCapabilityWrapper()).addHandler(value)
    }

    override fun clear() {
        (0 until value.tanks).map {
            value[it] = FluidStack.EMPTY
        }
    }
}