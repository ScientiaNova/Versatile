package com.scientianovateam.versatile.machines.recipes.components.ingredients.fluids

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.toJson
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.machines.recipes.components.IRecipeComponentHandler
import com.scientianovateam.versatile.machines.recipes.components.IRecipeHandlerSerializer
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeFluidStackSerializer
import com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks.ChancedRecipeStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fluids.FluidStack

class FluidOutputsHandler(override val value: List<ChancedRecipeStack<FluidStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<FluidStack>>> {
    override val pairedComponentType = FluidOutputsComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<FluidOutputsHandler>("fluid_outputs".toResLocV()) {
        override fun read(json: JsonElement) = when (json) {
            is JsonObject -> FluidOutputsHandlerIntermediate(listOf(ChancedRecipeFluidStackSerializer.read(json)))
            is JsonArray -> FluidOutputsHandlerIntermediate(json.map { ChancedRecipeFluidStackSerializer.read(it.asJsonObject) })
            else -> error("Invalid fluid outputs for recipe")
        }

        override fun write(obj: FluidOutputsHandler) = obj.value.map { ChancedRecipeFluidStackSerializer.write(it) }.toJson()

        override fun read(packet: PacketBuffer) = FluidOutputsHandler(List(packet.readVarInt()) { ChancedRecipeFluidStackSerializer.read(packet) })

        override fun write(packet: PacketBuffer, obj: FluidOutputsHandler) {
            packet.writeVarInt(obj.value.size)
            obj.value.forEach { ChancedRecipeFluidStackSerializer.write(packet, it) }
        }
    }
}