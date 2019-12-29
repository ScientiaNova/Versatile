package com.scientianovateam.versatile.machines.recipes.components.time

import com.google.gson.JsonElement
import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.machines.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.velisp.convertToExpression
import net.minecraft.network.PacketBuffer

class TimeHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = TimeComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<TimeHandler>("time".toResLocV()) {
        override fun write(obj: TimeHandler) = obj.value.toJson()

        override fun read(json: JsonElement) = TimeHandlerIntermediate(convertToExpression(json))

        override fun read(packet: PacketBuffer) = TimeHandler(packet.readVarInt())

        override fun write(packet: PacketBuffer, obj: TimeHandler) {
            packet.writeVarInt(obj.value)
        }
    }
}