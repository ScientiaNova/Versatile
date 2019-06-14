package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.element.ElementalProperties;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class IngotMaterial extends DustMaterial {
    private int meltingTemperature;
    private int boilingTemperature;

    public IngotMaterial(String name, TextureType textureType, int color, int tier) {
        super(name, textureType, color, tier);
    }

    public IngotMaterial addTags(String... tags) {
        super.addTags(tags);
        return this;
    }

    public IngotMaterial setItemTier(IItemTier tier) {
        super.setItemTier(tier);
        return this;
    }

    public IngotMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public IngotMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, () -> Ingredient.fromTag(getTag(MaterialRegistry.INGOT))));
    }

    public IngotMaterial setItemTierStats(int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn) {
        return setItemTierStats(getTier() + 1, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn);
    }

    public IngotMaterial setArmorMaterial(IArmorMaterial mat) {
        super.setArmorMaterial(mat);
        return this;
    }

    public IngotMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, getName(), toughness));
    }

    public IngotMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, float toughness) {
        return setArmorStats(durability, damageRecudtiom, enchantability, souldEvent, () -> Ingredient.fromTag(getTag(MaterialRegistry.INGOT)), toughness);
    }

    public IngotMaterial setHarvestTier(HarvestTier harvestTier) {
        super.setHarvestTier(harvestTier);
        return this;
    }

    public IngotMaterial setHarvestProperties(float hardness, float resistance, int level) {
        return setHarvestTier(new HarvestTier(hardness, resistance, level));
    }

    public IngotMaterial setHarvestProperties(float hardness, float resistance) {
        return setHarvestProperties(hardness, resistance, getTier());
    }

    public IngotMaterial blacklistTypes(ObjectType... types) {
        super.blacklistTypes(types);
        return this;
    }

    public IngotMaterial setComposition(MaterialStack... stacks) {
        super.setComposition(stacks);
        return this;
    }

    public IngotMaterial setElementalProperties(ElementalProperties properties) {
        super.setElementalProperties(properties);
        return this;
    }

    public IngotMaterial setMeltingTemperature(int temperature) {
        meltingTemperature = temperature;
        return this;
    }

    public IngotMaterial setBoilingTemperature(int temperature) {
        boilingTemperature = temperature;
        return this;
    }

    public int getMeltingTemperature() {
        return meltingTemperature;
    }

    public int getBoilingTemperature() {
        return boilingTemperature;
    }

    public IngotMaterial setProccessingMultiplier(int multiplier) {
        super.setProccessingMultiplier(multiplier);
        return this;
    }

    public IngotMaterial setRefinedMaterial(DustMaterial material) {
        super.setRefinedMaterial(material);
        return this;
    }

    public IngotMaterial setUnrefinedColor(int color) {
        super.setUnrefinedColor(color);
        return this;
    }
}