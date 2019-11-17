package com.emosewapixel.pixellib.machines.properties.implementations

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.capabilities.FluidStackHandler
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.packets.NetworkHandler
import com.emosewapixel.pixellib.machines.packets.UpdateNBTSerializableProperty
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.nbt.CompoundNBT
import net.minecraftforge.fml.network.NetworkDirection

open class TEFluidInventoryProperty(value: FluidStackHandler, override val id: String, override val te: BaseTileEntity) : FluidInventoryProperty(value), ITEBoundProperty {
    constructor(te: BaseTileEntity, id: String, slots: Int, noOutput: IntRange, noInput: IntRange, capacity: Int = 10000) : this(
            object : FluidStackHandler(slots, noOutput, noInput, capacity) {
                override fun onContentsChanged(slot: Int) = te.update()
            }, id, te
    )

    constructor(te: BaseTileEntity, id: String, inputCount: Int, outputCount: Int, capacity: Int = 10000) : this(
            te, id, inputCount + outputCount, 0 until inputCount, inputCount until inputCount + outputCount, capacity
    )

    override fun detectAndSendChanges(container: BaseContainer) {
        if ((container.clientProperties[id] as? TEFluidInventoryProperty)?.value?.let {
                    it.tanks.indices.any { index -> it.tanks[index] == value.tanks[index] }
                } == false
        ) {
            NetworkHandler.CHANNEL.sendTo(UpdateNBTSerializableProperty(id, value.serializeNBT()), (container.playerInv.player as ServerPlayerEntity).connection.networkManager, NetworkDirection.PLAY_TO_CLIENT)
            (container.clientProperties[id] as TEFluidInventoryProperty).value.tanks = value.tanks
        }
    }

    override fun copy() = TEFluidInventoryProperty(value, id, te)

    override fun deserializeNBT(nbt: CompoundNBT) {
        value.deserializeNBT(nbt.getCompound(id))
    }

    override fun serializeNBT() = nbt {
        id to value
    }
}