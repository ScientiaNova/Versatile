package com.scientianovateam.versatile.recipes.components.energy.generation

import com.google.gson.JsonElement
import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.velisp.convertToExpression
import net.minecraft.network.PacketBuffer

class EnergyGenerationHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = EnergyGenerationComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<EnergyGenerationHandler>("energy_generated_per_tick".toResLocV()) {
        override fun write(obj: EnergyGenerationHandler) = obj.value.toJson()

        override fun read(json: JsonElement) = EnergyGenerationHandlerIntermediate(convertToExpression(json))

        override fun read(packet: PacketBuffer) = EnergyGenerationHandler(packet.readVarInt())

        override fun write(packet: PacketBuffer, obj: EnergyGenerationHandler) {
            packet.writeVarInt(obj.value)
        }
    }
}