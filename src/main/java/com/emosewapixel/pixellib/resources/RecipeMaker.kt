package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.extensions.json
import com.emosewapixel.pixellib.extensions.toJson
import com.emosewapixel.pixellib.extensions.toResLoc
import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation

//This class contains functions used for adding recipes that get injected on world load
object RecipeMaker {
    @JvmStatic
    fun addShapelessRecipe(name: ResourceLocation, group: String, output: ItemStack, vararg inputs: Any) =
            JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json")) {
                "type" to "minecraft:crafting_shapeless"
                "group" to group
                "ingredients" to inputs.map { inputToJson(it) }
                "result" to output
            }

    @JvmStatic
    fun addShapelessRecipe(name: ResourceLocation, output: ItemStack, vararg inputs: Any) = addShapelessRecipe(name, "", output, inputs)

    @JvmStatic
    fun addShapelessRecipe(name: String, output: ItemStack, vararg inputs: Any) = addShapelessRecipe(name.toResLoc(), output, inputs)

    @JvmStatic
    fun addShapedRecipe(name: ResourceLocation, group: String, output: ItemStack, vararg shape: Any) =
            JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json")) {
                "type" to "minecraft:crafting_shaped"
                "group" to group
                val end = listOf(*shape)
                "pattern" to end.takeWhile { it is String }.map { (it as String).toJson() }
                "key" {
                    end.dropWhile { it is String }.zipWithNext().filterIndexed { index, _ -> index % 2 == 0 }.forEach {
                        it.first.toString() to inputToJson(it.second)
                    }
                }
                "result" to output
            }

    @JvmStatic
    fun addShapedRecipe(name: ResourceLocation, output: ItemStack, vararg shape: Any) = addShapedRecipe(name, "", output, shape)

    @JvmStatic
    fun addShapedRecipe(name: String, output: ItemStack, vararg shape: Any) = addShapedRecipe(name.toResLoc(), output, shape)

    @JvmStatic
    @JvmOverloads
    fun addFurnaceRecipe(name: ResourceLocation, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) =
            JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json")) {
                "type" to "minecraft:smelting"
                "ingredient" to inputToJson(input)
                "result" to output.registryName!!.toString()
                "cookingtime" to cookTime
                "experience" to xp
            }

    @JvmStatic
    fun removeRecipe(name: ResourceLocation) {
        if (name in JSONAdder.DATA)
            JSONAdder.DATA.remove(name)
        else
            JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json"), JsonObject())
    }

    @JvmStatic
    fun inputToJson(obj: Any) = json {
        when (obj) {
            is Item -> "item" to obj.registryName!!.toString()
            is Tag<*> -> "tag" to obj.id.toString()
        }
    }
}