package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.common.serialization.IRegisterableSerializer
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.velisp.toExpression
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketBuffer

data class RecipeItemStack(val stack: ItemStack) : IRecipeStack<ItemStack> {
    override val count = stack.count

    override val stacks = listOf(stack.copy())

    override val singleStack = stack.copy()

    override val names = setOf(stack.item.registryName!!)

    override fun matches(other: ItemStack) = stack.isItemEqual(other) && count <= other.count

    override fun matchesWithoutCount(other: ItemStack) = stack.isItemEqual(other)

    override val serializer = Serializer

    object Serializer : IRegisterableSerializer<RecipeItemStack, JsonObject> {
        override val registryName = "versatile:item_stack".toResLoc()

        override fun read(json: JsonObject): RecipeItemStackIntermediate {
            val item = json.get("item")?.toExpression()
                    ?: throw error("Didn't specify item in item stack")
            val count = json.get("count")?.toExpression() ?: NumberValue(1)
            return RecipeItemStackIntermediate(item, count)
        }

        override fun write(obj: RecipeItemStack) = json {
            "item" to obj.stack.item
            if (obj.count != 1) "count" to obj.count
        }

        override fun read(packet: PacketBuffer) = RecipeItemStack(packet.readItemStack())

        override fun write(packet: PacketBuffer, obj: RecipeItemStack) {
            packet.writeItemStack(obj.stack)
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