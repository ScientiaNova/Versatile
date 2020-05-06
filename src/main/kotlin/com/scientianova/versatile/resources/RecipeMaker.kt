@file:JvmName("RecipeMaker")

package com.scientianova.versatile.resources

import com.google.gson.JsonObject
import com.scientianova.versatile.common.extensions.json
import com.scientianova.versatile.common.extensions.toJson
import com.scientianova.versatile.common.extensions.toResLoc
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation

fun addShapelessRecipe(name: ResourceLocation, group: String, output: ItemStack, vararg inputs: Any) =
        addDataJSON(ResourceLocation(name.namespace, "recipes/${name.path}.json")) {
            "type" to "minecraft:crafting_shapeless"
            "group" to group
            "ingredients" to inputs.map { inputToJson(it) }
            "result" to output
        }

fun addShapelessRecipe(name: ResourceLocation, output: ItemStack, vararg inputs: Any) = addShapelessRecipe(name, "", output, *inputs)

fun addShapelessRecipe(name: String, output: ItemStack, vararg inputs: Any) = addShapelessRecipe(name.toResLoc(), output, *inputs)

fun addShapedRecipe(name: ResourceLocation, group: String, output: ItemStack, vararg shape: Any) =
        addDataJSON(ResourceLocation(name.namespace, "recipes/${name.path}.json")) {
            "type" to "minecraft:crafting_shaped"
            "group" to group
            "pattern" to shape.takeWhile { it is String }.map { (it as String).toJson() }
            "key" {
                shape.dropWhile { it is String }.zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }.forEach {
                    it.first.toString() to inputToJson(it.second)
                }
            }
            "result" to output
        }

fun addShapedRecipe(name: ResourceLocation, output: ItemStack, vararg shape: Any) = addShapedRecipe(name, "", output, *shape)

fun addShapedRecipe(name: String, output: ItemStack, vararg shape: Any) = addShapedRecipe(name.toResLoc(), output, *shape)

fun addCookingRecipe(type: String, name: ResourceLocation, input: Any, output: Item, cookTime: Int, xp: Float) =
        addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json")) {
            "type" to type
            "ingredient" to inputToJson(input)
            "result" to output.registryName!!.toString()
            "cookingtime" to cookTime
            "experience" to xp
        }

@JvmOverloads
fun addFurnaceRecipe(name: ResourceLocation, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) =
        addCookingRecipe("minecraft:smelting", name, input, output, cookTime, xp)

@JvmOverloads
fun addFurnaceRecipe(name: String, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) =
        addFurnaceRecipe(name.toResLoc(), input, output, cookTime, xp)

@JvmOverloads
fun addBlastingRecipe(name: ResourceLocation, input: Any, output: Item, cookTime: Int = 100, xp: Float = 0f) =
        addCookingRecipe("minecraft:blasting", name, input, output, cookTime, xp)

@JvmOverloads
fun addBlastingRecipe(name: String, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) =
        addBlastingRecipe(name.toResLoc(), input, output, cookTime, xp)

@JvmOverloads
fun addSmokerRecipe(name: ResourceLocation, input: Any, output: Item, cookTime: Int = 100, xp: Float = 0f) =
        addCookingRecipe("minecraft:smoking", name, input, output, cookTime, xp)

@JvmOverloads
fun addSmokerRecipe(name: String, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) =
        addSmokerRecipe(name.toResLoc(), input, output, cookTime, xp)

@JvmOverloads
fun addCampfireRecipe(name: ResourceLocation, input: Any, output: Item, cookTime: Int = 600, xp: Float = 0.35f) =
        addCookingRecipe("minecraft:campfire_cooking", name, input, output, cookTime, xp)

@JvmOverloads
fun addCampfireRecipe(name: String, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) =
        addCampfireRecipe(name.toResLoc(), input, output, cookTime, xp)

fun removeRecipe(name: ResourceLocation) {
    if (name in DATA) DATA.remove(name)
    else addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json"), JsonObject())
}

fun inputToJson(obj: Any) = json {
    when (obj) {
        is Item -> "item" to obj.registryName!!.toString()
        is Block -> "item" to obj.registryName!!.toString()
        is Tag<*> -> "tag" to obj.id.toString()
    }
}
