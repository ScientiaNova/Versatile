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

class FluidInputsHandler(override val value: List<ChancedRecipeStack<FluidStack>>) : IRecipeComponentHandler<List<ChancedRecipeStack<FluidStack>>> {
    override val pairedComponentType = FluidInputsComponent::class.java
    override val serializer = Serializer

    object Serializer : IRecipeHandlerSerializer<FluidInputsHandler>("fluid_inputs".toResLocV()) {
        override fun read(json: JsonElement) = when (json) {
            is JsonObject -> FluidInputsHandlerIntermediate(listOf(ChancedRecipeFluidStackSerializer.read(json)))
            is JsonArray -> FluidInputsHandlerIntermediate(json.map { ChancedRecipeFluidStackSerializer.read(it.asJsonObject) })
            else -> error("Invalid fluid inputs for recipe")
        }

        override fun write(obj: FluidInputsHandler) = obj.value.map { ChancedRecipeFluidStackSerializer.write(it) }.toJson()

        override fun read(packet: PacketBuffer) = FluidInputsHandler(List(packet.readVarInt()) { ChancedRecipeFluidStackSerializer.read(packet) })

        override fun write(packet: PacketBuffer, obj: FluidInputsHandler) {
            packet.writeVarInt(obj.value.size)
            obj.value.forEach { ChancedRecipeFluidStackSerializer.write(packet, it) }
        }
    }
}