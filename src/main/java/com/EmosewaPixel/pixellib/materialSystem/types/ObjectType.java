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

/*
Object Types are objects used for generating blocks/items/fluids for certain materials.
This is the base class that shouldn't be used for generating anything
*/
public class ObjectType {
    private String name;
    private int volume = 0;
    private Predicate<Material> requirement;
    private List<String> tags = new ArrayList<>();

    public ObjectType(String name, Predicate<Material> requirement) {
        this.name = name;
        this.requirement = requirement;
    }

    public ObjectType addTypeTag(String... tags) {
        this.tags.addAll(Arrays.asList(tags));
        return this;
    }

    public ObjectType andRequires(Predicate<Material> requirement) {
        this.requirement = this.requirement.and(requirement);
        return this;
    }

    public ObjectType orRequires(Predicate<Material> requirement) {
        this.requirement = this.requirement.or(requirement);
        return this;
    }

    public ObjectType setBucketVolume(int mb) {
        volume = mb;
        return this;
    }

    public ObjectType build() {
        ObjTypes.add(this);
        return ObjTypes.get(name);
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

    public Tag<Item> getItemTag() {
        return new ItemTags.Wrapper(new ResourceLocation("forge", name + "s"));
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public List<String> getTypeTags() {
        return tags;
    }

    public void merge(ObjectType type) {
        if (type.getBucketVolume() != 0)
        volume = type.getBucketVolume();
        orRequires(type::isMaterialCompatible);
        tags.addAll(type.getTypeTags());
    }
}