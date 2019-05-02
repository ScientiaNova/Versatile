package com.EmosewaPixel.pixellib.items.tools;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialAxe extends ItemAxe implements IMaterialItem {
    private Material material;

    public MaterialAxe(Material mat) {
        super(mat.getItemTier(), 6, -3.4F + mat.getItemTier().getEfficiency() / 2 * 0.1F, (new Properties()).group(PixelLib.main));
        setRegistryName("pixellib:" + mat.getName() + "_axe");
        this.material = mat;
        MaterialItems.addItem(this);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ObjectType getObjType() {
        return MaterialRegistry.AXE;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TextComponentTranslation("itemtype.axe.name", material.getTranslationKey());
    }
}
