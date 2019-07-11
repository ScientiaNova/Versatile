package com.EmosewaPixel.pixellib.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;

//This class contains functions used for adding recipes that get injected on world load
public class RecipeMaker {
    public static void addShapelessRecipe(ResourceLocation name, String group, ItemStack output, @Nonnull Object... inputs) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shapeless");
        recipe.addProperty("group", group);
        JsonArray ingredients = new JsonArray();
        Arrays.stream(inputs).map(RecipeMaker::inputToJsn).forEach(ingredients::add);
        recipe.add("ingredients", ingredients);
        recipe.add("result", stackToJson(output));
        JSONAdder.addDataJSON(new ResourceLocation(name.getNamespace(), "recipes/" + name.getPath() + ".json"), recipe);
    }

    public static void addShapelessRecipe(ResourceLocation name, ItemStack output, @Nonnull Object... inputs) {
        addShapelessRecipe(name, "", output, inputs);
    }

    public static void addShapedRecipe(ResourceLocation name, String group, ItemStack output, @Nonnull Object... shape) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:crafting_shaped");
        recipe.addProperty("group", group);
        JsonArray pattern = new JsonArray();
        Iterator<Object> shapeIterator = Arrays.asList(shape).iterator();
        Object current = shapeIterator.next();
        while (current instanceof String) {
            pattern.add((String) current);
            current = shapeIterator.next();
        }
        recipe.add("pattern", pattern);
        JsonObject key = new JsonObject();
        while (current instanceof Character && shapeIterator.hasNext()) {
            key.add(current.toString(), inputToJsn(shapeIterator.next()));
            if (shapeIterator.hasNext())
                current = shapeIterator.next();
        }
        recipe.add("key", key);
        recipe.add("result", stackToJson(output));
        JSONAdder.addDataJSON(new ResourceLocation(name.getNamespace(), "recipes/" + name.getPath() + ".json"), recipe);
    }

    public static void addShapedRecipe(ResourceLocation name, ItemStack output, @Nonnull Object... shape) {
        addShapedRecipe(name, "", output, shape);
    }

    public static void addFurnaceRecipe(ResourceLocation name, Object input, Item output, int cookTime, float xp) {
        JsonObject recipe = new JsonObject();
        recipe.addProperty("type", "minecraft:smelting");
        recipe.add("ingredient", inputToJsn(input));
        recipe.addProperty("result", output.getRegistryName().toString());
        recipe.addProperty("cookingtime", cookTime);
        recipe.addProperty("experience", xp);
        JSONAdder.addDataJSON(new ResourceLocation(name.getNamespace(), "recipes/" + name.getPath() + ".json"), recipe);
    }

    public static void addFurnaceRecipe(ResourceLocation name, Object input, Item output) {
        addFurnaceRecipe(name, input, output, 200, 0.1f);
    }

    public static void removeRecipe(ResourceLocation name) {
        JSONAdder.addDataJSON(new ResourceLocation(name.getNamespace(), "recipes/" + name.getPath() + ".json"), new JsonObject());
    }

    public static JsonObject inputToJsn(Object obj) {
        JsonObject json = new JsonObject();
        if (obj instanceof Item)
            json.addProperty("item", ((Item) obj).getRegistryName().toString());
        if (obj instanceof Tag)
            json.addProperty("tag", ((Tag) obj).getId().toString());
        return json;
    }

    public static JsonObject stackToJson(ItemStack stack) {
        JsonObject json = new JsonObject();
        json.addProperty("item", stack.getItem().getRegistryName().toString());
        json.addProperty("count", stack.getCount());
        return json;
    }
}