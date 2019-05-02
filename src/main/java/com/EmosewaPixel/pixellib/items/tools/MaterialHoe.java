package com.EmosewaPixel.pixellib.items.tools;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialHoe extends ItemHoe implements IMaterialItem {
    private Material material;

    public MaterialHoe(Material mat) {
        super(mat.getItemTier(), mat.getItemTier().getHarvestLevel() - 3, (new Properties()).group(PixelLib.main));
        setRegistryName("pixellib:" + mat.getName() + "_hoe");
        this.material = mat;
        MaterialItems.addItem(this);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ObjectType getObjType() {
        return MaterialRegistry.HOE;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TextComponentTranslation("itemtype.hoe.name", material.getTranslationKey());
    }
}