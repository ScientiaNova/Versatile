package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.registries.ForgeRegistries

class RecipeItemStack(val stack: ItemStack) : IRecipeStack<ItemStack> {
    override val count = stack.count

    override val stacks = listOf(stack.copy())

    override fun matches(other: ItemStack) = stack.isItemEqual(other) && count <= other.count

    override fun matchesWithoutCount(other: ItemStack) = stack.isItemEqual(other)

    override fun toString() = "item:" + stack.item.registryName

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeItemStack && ItemStack.areItemStacksEqual(stack, other.stack)

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<RecipeItemStack, JsonObject> {
        override val registryName = "versatile:item_stack".toResLoc()

        override fun read(json: JsonObject): RecipeItemStack {
            val item = json.getAsJsonPrimitive("item")?.asString?.toResLoc()?.let { ForgeRegistries.ITEMS.getValue(it) }
                    ?: Items.AIR
            val count = if (json.has("count")) json.getAsJsonPrimitive("count").asNumber?.toInt() ?: 1 else 1
            return RecipeItemStack(item * count)
        }

        override fun write(obj: RecipeItemStack) = json {
            "item" to obj.stack.item
            if (obj.count > 1) "count" to obj.count
        }
    }

    companion object {
        @JvmField
        val EMPTY = RecipeItemStack(ItemStack.EMPTY)
    }
}

operator fun MutableCollection<IRecipeStack<ItemStack>>.plusAssign(stack: ItemStack) = plusAssign(RecipeItemStack(stack))
fun Item.toRStack(count: Int = 1) = RecipeItemStack(this.toStack(count))
fun ItemStack.r() = RecipeItemStack(this)