package com.EmosewaPixel.pixellib.materialSystem.types;

import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

public class ObjectType {
    private String name;
    private Predicate<Material> requirement;

    public ObjectType(String name, Predicate<Material> requirement) {
        this.name = name;
        this.requirement = requirement;
        ObjTypes.add(this);
    }

    public String getName() {
        return name;
    }

    public boolean isMaterialCompatible(Material mat) {
        return requirement.test(mat);
    }

    public Tag<Item> getTag() {
        return new ItemTags.Wrapper(new ResourceLocation("forge", name + "s"));
    }
}
