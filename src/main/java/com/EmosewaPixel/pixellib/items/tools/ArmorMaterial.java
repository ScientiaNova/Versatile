package com.EmosewaPixel.pixellib.items.tools;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class ArmorMaterial implements IArmorMaterial {
    private final int durability;
    private final int damageRecudtiom;
    private final int enchantability;
    private final SoundEvent souldEvent;
    private final Supplier<Ingredient> repairMaterial;
    private final String name;
    private final float toughness;

    public ArmorMaterial(int durability, int damageRecudtiom, int enchantability, SoundEvent souldEvent, Supplier<Ingredient> repairMaterial, String name, float toughness) {
        this.durability = durability;
        this.damageRecudtiom = damageRecudtiom;
        this.enchantability = enchantability;
        this.souldEvent = souldEvent;
        this.repairMaterial = repairMaterial;
        this.name = name;
        this.toughness = toughness;
    }

    @Override
    public int getDurability(EntityEquipmentSlot slotIn) {
        return durability;
    }

    @Override
    public int getDamageReductionAmount(EntityEquipmentSlot slotIn) {
        return damageRecudtiom;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return souldEvent;
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
