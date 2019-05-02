package com.EmosewaPixel.pixellib.items.tools;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialSword extends ItemSword implements IMaterialItem {
    private Material material;

    public MaterialSword(Material mat) {
        super(mat.getItemTier(), 3, -2.4F, (new Properties()).group(PixelLib.main));
        setRegistryName("pixellib:" + mat.getName() + "_sword");
        this.material = mat;
        MaterialItems.addItem(this);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ObjectType getObjType() {
        return MaterialRegistry.SWORD;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TextComponentTranslation("itemtype.sword.name", material.getTranslationKey());
    }
}
