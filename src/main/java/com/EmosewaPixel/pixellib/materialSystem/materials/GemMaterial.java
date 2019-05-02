package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

public class GemMaterial extends DustMaterial {

    public GemMaterial(String name, TextureType textureType, int color) {
        super(name, textureType, color);
    }

    public GemMaterial hasOre() {
        super.hasOre();
        return this;
    }

    public GemMaterial setItemTier(IItemTier tier) {
        super.setItemTier(tier);
        return this;
    }

    public GemMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Ingredient repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    public GemMaterial setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, Ingredient.fromTag(getTag(MaterialRegistry.GEM))));
    }

    public GemMaterial setArmorMaterial(IArmorMaterial mat) {
        super.setArmorMaterial(mat);
        return this;
    }

    public GemMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Ingredient repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageRecudtiom, enchantability, souldEvent, repairMaterial, getName(), toughness));
    }

    public GemMaterial setArmorStats(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, float toughness) {
        return setArmorStats(durability, damageRecudtiom, enchantability, souldEvent, Ingredient.fromTag(getTag(MaterialRegistry.GEM)), toughness);
    }

    public GemMaterial setBlockHarvestTier(IHarvestTier tier) {
        super.setHarvestTier(tier);
        return this;
    }

    public GemMaterial disableSimpleProcessing() {
        super.disableSimpleProcessing();
        return this;
    }
}