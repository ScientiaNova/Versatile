package com.EmosewaPixel.pixellib.items.tools;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

//This is an implementation of vanilla's IArmorMaterial so you can simply make objects of it
public class ArmorMaterial implements IArmorMaterial {
    private final int durability;
    private final int damageReduction;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final Supplier<Ingredient> repairMaterial;
    private final String name;
    private final float toughness;

    public ArmorMaterial(int durability, int damageReduction, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, String name, float toughness) {
        this.durability = durability;
        this.damageReduction = damageReduction;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.repairMaterial = repairMaterial;
        this.name = name;
        this.toughness = toughness;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return durability;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return damageReduction;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }
}
