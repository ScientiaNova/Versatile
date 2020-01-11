package com.scientianovateam.versatile.recipes.components.energy.consumption

import com.google.gson.JsonElement
import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.velisp.toExpression
import net.minecraft.network.PacketBuffer

class EnergyConsumptionHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyConsumptionComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<EnergyConsumptionHandler>("energy_consumed_per_tick".toResLocV()) {
        override fun write(obj: EnergyConsumptionHandler) = obj.value.toJson()

        override fun read(json: JsonElement) = EnergyConsumptionHandlerIntermediate(json.toExpression())

        override fun read(packet: PacketBuffer) = EnergyConsumptionHandler(packet.readVarInt())

        override fun write(packet: PacketBuffer, obj: EnergyConsumptionHandler) {
            packet.writeVarInt(obj.value)
        }
    }
}