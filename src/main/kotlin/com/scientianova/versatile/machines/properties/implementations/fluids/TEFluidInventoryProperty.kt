package com.scientianova.versatile.machines.properties.implementations.fluids

import com.scientianova.versatile.common.extensions.get
import com.scientianova.versatile.common.extensions.getOrAddInstance
import com.scientianova.versatile.common.extensions.nbt
import com.scientianova.versatile.common.extensions.set
import com.scientianova.versatile.machines.BaseTileEntity
import com.scientianova.versatile.machines.capabilities.fluids.FluidCapabilityWrapper
import com.scientianova.versatile.machines.capabilities.fluids.FluidStackHandler
import com.scientianova.versatile.machines.capabilities.fluids.IFluidHandlerModifiable
import com.scientianova.versatile.machines.gui.BaseContainer
import com.scientianova.versatile.machines.packets.NetworkHandler
import com.scientianova.versatile.machines.packets.handlers.UpdateTankPacket
import com.scientianova.versatile.machines.properties.ITEBoundProperty
import com.scientianova.versatile.machines.properties.IValueProperty
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