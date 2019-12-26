package com.scientianovateam.versatile.machines.recipes.components.ingredients.recipestacks

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toResLoc
import com.scientianovateam.versatile.common.serialization.IRegisterableJSONSerializer
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.TagStack
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.times
import com.scientianovateam.versatile.machines.recipes.components.ingredients.utility.toStack
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tags.ItemTags
import net.minecraft.tags.Tag

class RecipeItemTagStack(stack: TagStack<Item>) : IRecipeStack<ItemStack> {
    val tag = stack.tag

    override val count = stack.count

    override val stacks get() = tag.allElements.map { it * count }

    override fun matches(other: ItemStack) = count <= other.count && other.item in tag

    override fun matchesWithoutCount(other: ItemStack) = other.item in tag

    override fun toString() = "item_tag:" + tag.id

    override fun hashCode() = toString().hashCode()

    override fun equals(other: Any?) = other is RecipeItemTagStack && other.tag.id == tag.id && other.count == count

    override val serializer = Serializer

    object Serializer : IRegisterableJSONSerializer<RecipeItemTagStack, JsonObject> {
        override val registryName = "versatile:tag_stack".toResLoc()

        override fun read(json: JsonObject): RecipeItemTagStack {
            val tag = json.getAsJsonPrimitive("tag")?.asString?.toResLoc()?.let { ItemTags.Wrapper(it) }
                    ?: ItemTags.Wrapper("empty".toResLoc())
            val count = if (json.has("count")) json.getAsJsonPrimitive("count").asNumber?.toInt() ?: 1 else 1
            return RecipeItemTagStack(tag * count)
        }

        override fun write(obj: RecipeItemTagStack) = json {
            "tag" to obj.tag
            if (obj.count > 1) "count" to obj.count
        }
    }
}

operator fun MutableCollection<IRecipeStack<ItemStack>>.plusAssign(stack: TagStack<Item>) = plusAssign(RecipeItemTagStack(stack))
fun Tag<Item>.toRStack(count: Int = 1) = RecipeItemTagStack(this.toStack(count))
fun TagStack<Item>.r() = RecipeItemTagStack(this)