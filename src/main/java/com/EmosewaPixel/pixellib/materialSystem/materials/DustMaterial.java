package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class DustMaterial extends Material {
    private IHarvestTier harvestTier = null;
    private boolean simpleProcessing = true;

    public DustMaterial(String name, TextureType textureType, int color) {
        super(name, textureType, color);
    }

    public DustMaterial hasOre() {
        super.hasOre();
        return this;
    }

    public DustMaterial setItemTier(IItemTier tier) {
        super.setItemTier(tier);
        return this;
    }

    public DustMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Ingredient repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public DustMaterial setArmorMaterial(IArmorMaterial mat) {
        super.setArmorMaterial(mat);
        return this;
    }

    public DustMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Ingredient repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, getName(), toughness));
    }

    public IHarvestTier getHarvestTier() {
        return harvestTier;
    }

    public DustMaterial disableSimpleProcessing() {
        simpleProcessing = false;
        return this;
    }

    public void setHarvestTier(IHarvestTier harvestTier) {
        this.harvestTier = harvestTier;
    }

    public boolean hasSimpleProcessing() {
        return simpleProcessing;
    }
}