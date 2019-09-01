package com.EmosewaPixel.pixellib.materialsystem.types;

import com.EmosewaPixel.pixellib.materialsystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialsystem.materials.Material;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;

import java.util.function.Function;
import java.util.function.Predicate;

//Block Types are Object Types used for generating Blocks
public class BlockType extends ObjectType {
    private Block.Properties properties;
    private Function<IMaterialItem, JsonObject> blockstateJson;

    public BlockType(String name, Predicate<Material> requirement, Block.Properties properties) {
        super(name, requirement);
        this.properties = properties;
        blockstateJson = i -> {
            JsonObject states = new JsonObject();
            JsonObject variants = new JsonObject();
            JsonObject variant = new JsonObject();
            variant.addProperty("model", "pixellib:block/materialblocks/" + (hasTag(MaterialRegistry.SINGLE_TEXTURE_TYPE) ? getName() : i.getMat().getTextureType().toString() + "/" + getName()));
            variants.add("", variant);
            states.add("variants", variants);
            return states;
        };
    }

    public BlockType setBlockstateJson(Function<IMaterialItem, JsonObject> blockstateJson) {
        this.blockstateJson = blockstateJson;
        return this;
    }

    public Block.Properties getProperties() {
        return properties;
    }

    public JsonObject getBlockstateJson(IMaterialItem item) {
        return blockstateJson.apply(item);
    }
}