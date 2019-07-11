package com.EmosewaPixel.pixellib.items;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.materialsystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialsystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialsystem.materials.Material;
import com.EmosewaPixel.pixellib.materialsystem.types.ObjectType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.translation.LanguageMap;

//Material Items are Items that have a Material and Object Type
public class MaterialItem extends Item implements IMaterialItem {
    private Material material;
    private ObjectType type;

    public MaterialItem(Material material, ObjectType type) {
        super(new Item.Properties().group(PixelLib.main));
        setRegistryName("pixellib:" + material.getName() + "_" + type.getName());
        this.material = material;
        this.type = type;
        MaterialItems.addItem(this);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ObjectType getObjType() {
        return type;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        if (LanguageMap.getInstance().exists(getTranslationKey()))
            return new TranslationTextComponent(getTranslationKey());

        return new TranslationTextComponent("itemtype." + type.getName(), material.getTranslationKey());
    }
}