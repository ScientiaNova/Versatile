package com.EmosewaPixel.pixellib.materialSystem.types;

import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ObjectType {
    private String name;
    private int volume = 0;
    private Predicate<Material> requirement;
    private List<String> tags = new ArrayList<>();

    public ObjectType(String name, Predicate<Material> requirement) {
        this.name = name;
        this.requirement = requirement;
        ObjTypes.add(this);
    }

    public ObjectType addTypeTag(String... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    public ObjectType setBucketVolume(int mb) {
        volume = mb;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getBucketVolume() {
        return volume;
    }

    public boolean isMaterialCompatible(Material mat) {
        return requirement.test(mat);
    }

    public Tag<Item> getTag() {
        return new ItemTags.Wrapper(new ResourceLocation("forge", name + "s"));
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }
}