package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.element.Element;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

//Ingot Materials are the solid materials that, above all else, have ingots
public class IngotMaterial extends DustMaterial {
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

    public IngotMaterial setArmorStats(int durability, int damageReduction, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageReduction, enchantability, soundEvent, repairMaterial, getName(), toughness));
    }

    public IngotMaterial setArmorStats(int durability, int damageReduction, int enchantability, SoundEvent soundEvent, float toughness) {
        return setArmorStats(durability, damageReduction, enchantability, soundEvent, () -> Ingredient.fromTag(getTag(MaterialRegistry.INGOT)), toughness);
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

    public IngotMaterial setElement(Element element) {
        super.setElement(element);
        return this;
    }

    public IngotMaterial setSecondName(String name) {
        super.setSecondName(name);
        return this;
    }

    public IngotMaterial setStandardBurnTime(int time) {
        super.setStandardBurnTime(time);
        return this;
    }

    public IngotMaterial setMeltingTemperature(int temperature) {
        super.setMeltingTemperature(temperature);
        return this;
    }

    public IngotMaterial setBoilingTemperature(int temperature) {
        super.setBoilingTemperature(temperature);
        return this;
    }

    public IngotMaterial setProcessingMultiplier(int multiplier) {
        super.setProcessingMultiplier(multiplier);
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

    public Item getDefaultItem() {
        return MaterialItems.getItem(this, MaterialRegistry.INGOT);
    }
}