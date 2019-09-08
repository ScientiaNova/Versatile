package com.emosewapixel.pixellib.resources

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.tags.Tag
import net.minecraft.util.ResourceLocation
import java.util.Arrays

//This class contains functions used for adding recipes that get injected on world load
object RecipeMaker {
    @JvmStatic
    @JvmOverloads
    fun addShapelessRecipe(name: ResourceLocation, group: String = "", output: ItemStack, vararg inputs: Any) {
        val recipe = JsonObject()
        recipe.addProperty("type", "minecraft:crafting_shapeless")
        recipe.addProperty("group", group)
        val ingredients = JsonArray()
        inputs.map { inputToJsn(it) }.forEach { ingredients.add(it) }
        recipe.add("ingredients", ingredients)
        recipe.add("result", stackToJson(output))
        JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json"), recipe)
    }

    @JvmStatic
    @JvmOverloads
    fun addShapedRecipe(name: ResourceLocation, group: String = "", output: ItemStack, vararg shape: Any) {
        val recipe = JsonObject()
        recipe.addProperty("type", "minecraft:crafting_shaped")
        recipe.addProperty("group", group)
        val pattern = JsonArray()
        val shapeIterator = Arrays.asList(*shape).iterator()
        var current = shapeIterator.next()
        while (current is String) {
            pattern.add(current)
            current = shapeIterator.next()
        }
        recipe.add("pattern", pattern)
        val key = JsonObject()
        while (current is Char && shapeIterator.hasNext()) {
            key.add(current.toString(), inputToJsn(shapeIterator.next()))
            if (shapeIterator.hasNext())
                current = shapeIterator.next()
        }
        recipe.add("key", key)
        recipe.add("result", stackToJson(output))
        JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json"), recipe)
    }

    @JvmStatic
    @JvmOverloads
    fun addFurnaceRecipe(name: ResourceLocation, input: Any, output: Item, cookTime: Int = 200, xp: Float = 0.1f) {
        val recipe = JsonObject()
        recipe.addProperty("type", "minecraft:smelting")
        recipe.add("ingredient", inputToJsn(input))
        recipe.addProperty("result", output.registryName!!.toString())
        recipe.addProperty("cookingtime", cookTime)
        recipe.addProperty("experience", xp)
        JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json"), recipe)
    }

    @JvmStatic
    fun removeRecipe(name: ResourceLocation) {
        if (JSONAdder.DATA.containsKey(name))
            JSONAdder.DATA.remove(name)
        else
            JSONAdder.addDataJSON(ResourceLocation(name.namespace, "recipes/" + name.path + ".json"), JsonObject())
    }

    @JvmStatic
    fun inputToJsn(obj: Any): JsonObject {
        val json = JsonObject()
        if (obj is Item)
            json.addProperty("item", obj.registryName!!.toString())
        if (obj is Tag<*>)
            json.addProperty("tag", obj.id.toString())
        return json
    }

    @JvmStatic
    fun stackToJson(stack: ItemStack): JsonObject {
        val json = JsonObject()
        json.addProperty("item", stack.item.registryName!!.toString())
        json.addProperty("count", stack.count)
        return json
    }
}