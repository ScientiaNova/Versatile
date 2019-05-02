package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class Material {
    private String name;
    private TextureType textureType;
    private int color;
    private boolean hasOre = false;
    private IItemTier itemTier = null;
    private IArmorMaterial armorMaterial = null;

    public Material(String name, TextureType textureType, int color) {
        this.name = name;
        this.textureType = textureType;
        this.color = color;
    }

    public Material hasOre() {
        this.hasOre = true;
        return this;
    }

    public Material setItemTier(IItemTier tier) {
        this.itemTier = tier;
        return this;
    }

    public Material setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Ingredient repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public Material setArmorMaterial(IArmorMaterial mat) {
        this.armorMaterial = mat;
        return this;
    }

    public Material setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Ingredient repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, name, toughness));
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

    public boolean doesHaveOre() {
        return hasOre;
    }

    public IArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public IItemTier getItemTier() {
        return itemTier;
    }

    public ITextComponent getTranslationKey() {
        return new TextComponentTranslation("material." + name + ".name");
    }

    public Tag<Item> getTag(ObjectType type) {
        return new ItemTags.Wrapper(new ResourceLocation("forge", type.getName() + "s/" + name));
    }
}