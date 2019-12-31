package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IGeneralSerializer
import com.scientianovateam.versatile.common.serialization.IRegisterableSerializer
import com.scientianovateam.versatile.recipes.RECIPE_FLUID_STACK_SERIALIZERS
import com.scientianovateam.versatile.recipes.RECIPE_ITEM_STACK_SERIALIZERS
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.fluids.RecipeFluidStackIntermediate
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items.RecipeItemStackIntermediate
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer
import net.minecraftforge.fluids.FluidStack

open class ChancedRecipeStack<T>(val value: IRecipeStack<T>, val chance: Float = 1f) : IRecipeStack<T> by value

fun <T> IRecipeStack<T>.chanced(chance: Float = 1f) = ChancedRecipeStack(this, chance)

object ChancedRecipeItemStackSerializer : IGeneralSerializer<ChancedRecipeStack<ItemStack>, JsonObject> {
    override fun read(json: JsonObject): ChancedRecipeStackIntermediate<ItemStack> {
        val stack = json.entrySet().firstOrNull { it.key != "chance" }?.let {
            RECIPE_ITEM_STACK_SERIALIZERS[it.key.toResLocV()]?.read(it.value.asJsonObject)
        } ?: RecipeItemStackIntermediate.EMPTY
        val chance = if (json.has("chance")) convertToExpression(json.getAsJsonPrimitive("chance")) else NumberValue(1)
        return ChancedRecipeStackIntermediate(stack, chance)
    }

    override fun write(obj: ChancedRecipeStack<ItemStack>) = json {
        obj.serializer.registryName to obj.value.serialize()
        if (obj.chance < 1f) "chance" to obj.chance
    }

    override fun read(packet: PacketBuffer) = ChancedRecipeStack(RECIPE_ITEM_STACK_SERIALIZERS[packet.readResourceLocation()]!!.read(packet), packet.readFloat())

    override fun write(packet: PacketBuffer, obj: ChancedRecipeStack<ItemStack>) {
        val serializer = obj.value.serializer
        packet.writeResourceLocation(serializer.registryName)
        serializer.writePacket(packet, obj.value)
        packet.writeFloat(obj.chance)
    }
}

object ChancedRecipeFluidStackSerializer : IGeneralSerializer<ChancedRecipeStack<FluidStack>, JsonObject> {
    override fun read(json: JsonObject): ChancedRecipeStackIntermediate<FluidStack> {
        val stack = json.entrySet().firstOrNull { it.key != "chance" }?.let {
            RECIPE_FLUID_STACK_SERIALIZERS[it.key.toResLocV()]?.read(it.value.asJsonObject)
        } ?: RecipeFluidStackIntermediate.EMPTY
        val chance = if (json.has("chance")) convertToExpression(json.getAsJsonPrimitive("chance")) else NumberValue(1)
        return ChancedRecipeStackIntermediate(stack, chance)
    }

    override fun write(obj: ChancedRecipeStack<FluidStack>) = json {
        obj.serializer.registryName to obj.value.serialize()
        if (obj.chance < 1f) "chance" to obj.chance
    }

    override fun read(packet: PacketBuffer) = ChancedRecipeStack(RECIPE_FLUID_STACK_SERIALIZERS[packet.readResourceLocation()]!!.read(packet), packet.readFloat())

    override fun write(packet: PacketBuffer, obj: ChancedRecipeStack<FluidStack>) {
        val serializer = obj.value.serializer
        packet.writeResourceLocation(serializer.registryName)
        serializer.writePacket(packet, obj.value)
        packet.writeFloat(obj.chance)
    }
}

@Suppress("UNCHECKED_CAST")
private fun <T, S : IRecipeStack<T>> IRegisterableSerializer<out S, JsonObject>.writePacket(packet: PacketBuffer, stack: S) = (this as IRegisterableSerializer<S, JsonObject>).write(packet, stack)