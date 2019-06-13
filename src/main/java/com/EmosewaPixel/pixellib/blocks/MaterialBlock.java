package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.materials.DustMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.translation.LanguageMap;

public class MaterialBlock extends ModBlock implements IMaterialItem {
    private DustMaterial material;
    private ObjectType type;

    public MaterialBlock(DustMaterial mat, BlockType type) {
        super(type.getProperties().hardnessAndResistance(mat.getHarvestTier().getHardness(), mat.getHarvestTier().getResistance()), "pixellib:" + mat.getName() + "_" + type.getName(), mat.getHarvestTier().getHarvestLevel());
        this.material = mat;
        this.type = type;
        MaterialBlocks.addBlock(this);
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
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public ITextComponent getNameTextComponent() {
        if (LanguageMap.getInstance().exists(getTranslationKey()))
            return new TranslationTextComponent(getTranslationKey());

        return new TranslationTextComponent("blocktype." + type.getName(), material.getTranslationKey());
    }
}