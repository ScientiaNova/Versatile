package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Material {
    private String name;
    private TextureType textureType;
    private int color;
    private IItemTier itemTier = null;
    private IArmorMaterial armorMaterial = null;
    private ArrayList<ObjectType> blacklist = new ArrayList<>();
    private int tier;
    private List<String> materialTags = new ArrayList<>();
    private ImmutableList<MaterialStack> composition = new ImmutableList.Builder<MaterialStack>().build();

    public Material(String name, TextureType textureType, int color, int tier) {
        this.name = name;
        this.textureType = textureType;
        this.color = color;
        this.tier = tier;
    }

    public Material addTags(String... tags) {
            materialTags.addAll(Arrays.asList(tags));
        return this;
    }

    public Material setItemTier(IItemTier tier) {
        this.itemTier = tier;
        return this;
    }

    public Material setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public Material setItemTierStats(int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(tier + 1, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public Material setArmorMaterial(IArmorMaterial mat) {
        this.armorMaterial = mat;
        return this;
    }

    public Material setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, name, toughness));
    }

    public Material blacklistTypes(ObjectType... types) {
        blacklist.addAll(Arrays.asList(types));
        return this;
    }

    public Material setComposition(MaterialStack... stacks) {
        this.composition.addAll(Arrays.asList(stacks));
        return this;
    }

    public Material build() {
        Materials.add(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public TextureType getTextureType() {
        return textureType;
    }

    public int getColor() {
        return color;
    }

    public IArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public IItemTier getItemTier() {
        return itemTier;
    }

    public ITextComponent getTranslationKey() {
        return new TranslationTextComponent("material." + name + ".name");
    }

    public Tag<Item> getTag(ObjectType type) {
        return new ItemTags.Wrapper(new ResourceLocation("forge", type.getName() + "s/" + name));
    }

    public ImmutableList<MaterialStack> getComposition() {
        return composition;
    }

    public boolean hasBlacklisted(ObjectType type) {
        return blacklist.contains(type);
    }

    public int getTier() {
        return tier;
    }

    public boolean hasTag(String tag) {
        return materialTags.contains(tag);
    }
}