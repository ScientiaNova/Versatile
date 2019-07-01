package com.EmosewaPixel.pixellib.materialSystem.materials;

import com.EmosewaPixel.pixellib.items.tools.ArmorMaterial;
import com.EmosewaPixel.pixellib.items.tools.ItemTier;
import com.EmosewaPixel.pixellib.materialSystem.element.Element;
import com.EmosewaPixel.pixellib.materialSystem.element.Elements;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.materialSystem.types.TextureType;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/*
Materials are objects used for generating items/blocks/fluids based on object types. They have a wide range of customizability.
However, the base Materials aren't meant to be used for generating anything
*/
public class Material {
    private String name;
    private TextureType textureType;
    private int color;
    private IItemTier itemTier = null;
    private IArmorMaterial armorMaterial = null;
    private ArrayList<ObjectType> blacklist = new ArrayList<>();
    private int tier;
    private List<String> materialTags = new ArrayList<>();
    private ImmutableList<MaterialStack> composition = new ImmutableList.Builder<MaterialStack>().build();
    private Element element = Elements.NULL;
    private String secondName = null;
    private int burnTime = 0;

    public Material(String name, TextureType textureType, int color, int tier) {
        this.name = name;
        this.textureType = textureType;
        this.color = color;
        this.tier = tier;
    }

    //Adds tags to the material, mainly used for generating object types and recipes for this material
    public Material addTags(String... tags) {
        materialTags.addAll(Arrays.asList(tags));
        return this;
    }

    //Sets the item tier this material has the stats of
    public Material setItemTier(IItemTier tier) {
        this.itemTier = tier;
        return this;
    }

    //Sets the stats used for creating tools for this material
    public Material setItemTierStats(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(harvestLevelIn, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    //Sets the stats used for creating armor for this material, using the material's tier as the harvest level
    public Material setItemTierStats(int maxUsesIn, float efficiencyIn, float attackDamageIn, int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
        return setItemTier(new ItemTier(tier + 1, maxUsesIn, efficiencyIn, attackDamageIn, enchantabilityIn, repairMaterialIn));
    }

    //Sets the armor material this material has the stats of
    public Material setArmorMaterial(IArmorMaterial mat) {
        this.armorMaterial = mat;
        return this;
    }

    //Sets the stats used for creating armor for this material
    public Material setArmorStats(int durability, int damageReduction, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, float toughness) {
        return setArmorMaterial(new ArmorMaterial(durability, damageReduction, enchantability, soundEvent, repairMaterial, name, toughness));
    }

    //Blacklists object types that shouldn't be generated for the material
    public Material blacklistTypes(ObjectType... types) {
        blacklist.addAll(Arrays.asList(types));
        return this;
    }

    //Sets the material composition of the material
    public Material setComposition(MaterialStack... stacks) {
        this.composition.addAll(Arrays.asList(stacks));
        return this;
    }

    //Sets the pure element the material is made of
    public Material setElement(Element element) {
        this.element = element;
        return this;
    }

    //Sets the second names for the materials' tags
    public Material setSecondName(String name) {
        this.secondName = name;
        return this;
    }

    //Sets the burn time for object types with a volume of 144mb, scales across object types
    public Material setStandardBurnTime(int time) {
        burnTime = time;
        return this;
    }

    public Material build() {
        Materials.add(this);
        return Materials.get(name);
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

    public IArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }

    public IItemTier getItemTier() {
        return itemTier;
    }

    public ITextComponent getTranslationKey() {
        return new TranslationTextComponent("material." + name);
    }

    public Tag<Item> getTag(ObjectType type) {
        return new ItemTags.Wrapper(new ResourceLocation("forge", type.getName() + "s/" + name));
    }

    public Tag<Item> getSecondTag(ObjectType type) {
        return new ItemTags.Wrapper(new ResourceLocation("forge", type.getName() + "s/" + secondName));
    }

    public ImmutableList<MaterialStack> getComposition() {
        return composition;
    }

    public List<MaterialStack> getFullComposition() {
        if (composition.size() == 0)
            return Collections.singletonList(new MaterialStack(this));

        return composition.stream().flatMap(ms -> ms.getMaterial().getFullComposition().stream()
                .map(m -> new MaterialStack(m.getMaterial(), m.getCount() * ms.getCount()))
        ).collect(Collectors.toList());
    }

    public boolean hasBlacklisted(ObjectType type) {
        return blacklist.contains(type);
    }

    public int getTier() {
        return tier;
    }

    public boolean hasTag(String tag) {
        return materialTags.contains(tag);
    }

    public Element getElement() {
        return element;
    }

    public boolean isPureElement() {
        return element != Elements.NULL;
    }

    public String getSecondName() {
        return secondName;
    }

    public boolean hasSecondName() {
        return secondName != null;
    }

    public int getStandardBurnTime() {
        return burnTime;
    }

    public ArrayList<ObjectType> getTypeBlacklist() {
        return blacklist;
    }

    public List<String> getMaterialTags() {
        return materialTags;
    }

    public void merge(Material mat) {
        if (mat.getItemTier() != null)
            itemTier = mat.getItemTier();
        if (mat.getArmorMaterial() != null)
            armorMaterial = mat.getArmorMaterial();
        blacklist.addAll(mat.getTypeBlacklist());
        materialTags.addAll(mat.getMaterialTags());
        if (mat.isPureElement())
            element = mat.getElement();
        if (mat.getSecondName() != null)
            secondName = mat.getSecondName();
        if (mat.getStandardBurnTime() != 0)
            burnTime = mat.getStandardBurnTime();
    }
}