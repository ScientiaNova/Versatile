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

public class IngotMaterial extends DustMaterial {

    public IngotMaterial(String name, TextureType textureType, int color, int tier) {
        super(name, textureType, color, tier);
    }

    public IngotMaterial hasOre() {
        super.hasOre();
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

    public IngotMaterial disableSimpleProcessing() {
        super.disableSimpleProcessing();
        return this;
    }

    public IngotMaterial blacklistTypes(ObjectType... types) {
        super.blacklistTypes(types);
        return this;
    }
}