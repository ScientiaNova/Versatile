package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class DustMaterial extends Material {
    private HarvestTier harvestTier = null;
    private boolean simpleProcessing = true;

    public DustMaterial(String name, TextureType textureType, int color, int tier) {
        super(name, textureType, color, tier);
        setHarvestProperties(1.5f * (getTier() + 1), 1.5f * (getTier() + 1));
    }

    public DustMaterial hasOre() {
        super.hasOre();
        return this;
    }

    public DustMaterial setItemTier(IItemTier tier) {
        super.setItemTier(tier);
        return this;
    }

    public DustMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public DustMaterial setItemTierStats(int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTierStats(getTier() + 1, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn);
    }

    public DustMaterial setArmorMaterial(IArmorMaterial mat) {
        super.setArmorMaterial(mat);
        return this;
    }

    public DustMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, getName(), toughness));
    }

    public DustMaterial setHarvestTier(HarvestTier harvestTier) {
        this.harvestTier = harvestTier;
        return this;
    }

    public DustMaterial setHarvestProperties(float hardness, float resistance, int level) {
        return setHarvestTier(new HarvestTier(hardness, resistance, level));
    }

    public DustMaterial setHarvestProperties(float hardness, float resistance) {
        return setHarvestProperties(hardness, resistance, getTier());
    }

    public DustMaterial disableSimpleProcessing() {
        simpleProcessing = false;
        return this;
    }

    public DustMaterial blacklistTypes(ObjectType... types) {
        super.blacklistTypes(types);
        return this;
    }

    public HarvestTier getHarvestTier() {
        return harvestTier;
    }

    public boolean hasSimpleProcessing() {
        return simpleProcessing;
    }
}