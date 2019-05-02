package com.EmosewaPixel.pixellib.items.tools;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class MaterialPick extends ItemPickaxe implements IMaterialItem {
    private Material material;

    public MaterialPick(Material mat) {
        super(mat.getItemTier(), 1, -2.8F, (new Item.Properties()).group(PixelLib.main));
        setRegistryName("pixellib:" + mat.getName() + "_pickaxe");
        this.material = mat;
        MaterialItems.addItem(this);
    }

    @Override
    public Material getMaterial() {
        return material;
    }

    @Override
    public ObjectType getObjType() {
        return MaterialRegistry.PICKAXE;
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TextComponentTranslation("itemtype.pickaxe.name", material.getTranslationKey());
    }
}