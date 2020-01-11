package com.scientianovateam.versatile.recipes.components.time

import com.google.gson.JsonElement
import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.velisp.toExpression
import net.minecraft.network.PacketBuffer

class TimeHandler(override val value: Int) : IRecipeComponentHandler<Int> {
    override val pairedComponentType = TimeComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<TimeHandler>("time".toResLocV()) {
        override fun write(obj: TimeHandler) = obj.value.toJson()

        override fun read(json: JsonElement) = TimeHandlerIntermediate(json.toExpression())

        override fun read(packet: PacketBuffer) = TimeHandler(packet.readVarInt())

        override fun write(packet: PacketBuffer, obj: TimeHandler) {
            packet.writeVarInt(obj.value)
        }
    }
}