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

public class GemMaterial extends DustMaterial {

    public GemMaterial(String name, TextureType textureType, int color, int tier) {
        super(name, textureType, color, tier);
    }

    public GemMaterial addTags(MaterialTag... tags) {
        super.addTags(tags);
        return this;
    }

    public GemMaterial setItemTier(IItemTier tier) {
        super.setItemTier(tier);
        return this;
    }

    public GemMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public GemMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn) {
        return setItemTierStats(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, () -> Ingredient.fromTag(getTag(MaterialRegistry.GEM)));
    }

    public GemMaterial setItemTierStats(int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn) {
        return setItemTierStats(getTier() + 1, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn);
    }

    public GemMaterial setArmorMaterial(IArmorMaterial mat) {
        super.setArmorMaterial(mat);
        return this;
    }

    public GemMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, getName(), toughness));
    }

    public GemMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, float toughness) {
        return setArmorStats(durability, damageRecudtiom, enchantability, souldEvent, () -> Ingredient.fromTag(getTag(MaterialRegistry.GEM)), toughness);
    }

    public GemMaterial setHarvestTier(HarvestTier harvestTier) {
        super.setHarvestTier(harvestTier);
        return this;
    }

    public GemMaterial setHarvestProperties(float hardness, float resistance, int level) {
        return setHarvestTier(new HarvestTier(hardness, resistance, level));
    }

    public GemMaterial setHarvestProperties(float hardness, float resistance) {
        return setHarvestProperties(hardness, resistance, getTier());
    }

    public GemMaterial blacklistTypes(ObjectType... types) {
        super.blacklistTypes(types);
        return this;
    }
}