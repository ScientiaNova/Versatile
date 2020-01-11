package com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.items

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IRegisterableSerializer
import com.scientianovateam.versatile.recipes.components.ingredients.recipestacks.IRecipeStack
import com.scientianovateam.versatile.recipes.components.ingredients.utility.TagStack
import com.scientianovateam.versatile.recipes.components.ingredients.utility.times
import com.scientianovateam.versatile.recipes.components.ingredients.utility.toStack
import com.scientianovateam.versatile.velisp.toExpression
import com.scientianovateam.versatile.velisp.evaluated.NumberValue
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.network.PacketBuffer
import net.minecraft.tags.ItemTags
import net.minecraft.tags.Tag

data class RecipeItemTagStack(val tag: Tag<Item>, override val count: Int = 1) : IRecipeStack<ItemStack> {
    constructor(stack: TagStack<Item>) : this(stack.tag, stack.count)

    override val stacks get() = tag.allElements.map { it * count }

    override val singleStack = (tag.allElements.minBy { it.registryName.toString() } ?: Items.AIR) * count

    override val names get() = tag.allElements.map { it.registryName!! }.toSet()

    override fun matches(other: ItemStack) = count <= other.count && other.item in tag

    override fun matchesWithoutCount(other: ItemStack) = other.item in tag

    override val serializer = Serializer

    object Serializer : IRegisterableSerializer<RecipeItemTagStack, JsonObject> {
        override val registryName = "versatile:tag_stack".toResLoc()

        override fun read(json: JsonObject): RecipeItemTagStackIntermediate {
            val tag = json.get("item")?.toExpression()
                    ?: throw error("Didn't specify tag in item tag stack")
            val count = json.get("count")?.toExpression() ?: NumberValue(1)
            return RecipeItemTagStackIntermediate(tag, count)
        }

        override fun write(obj: RecipeItemTagStack) = json {
            "tag" to obj.tag
            if (obj.count != 1) "count" to obj.count
        }

        override fun read(packet: PacketBuffer) = RecipeItemTagStack(ItemTags.Wrapper(packet.readResourceLocation()) * packet.readVarInt())

        override fun write(packet: PacketBuffer, obj: RecipeItemTagStack) {
            packet.writeResourceLocation(obj.tag.id)
            packet.writeVarInt(obj.count)
        }
    }
}

fun Tag<Item>.toRStack(count: Int = 1) = RecipeItemTagStack(this.toStack(count))
fun TagStack<Item>.r() = RecipeItemTagStack(this)