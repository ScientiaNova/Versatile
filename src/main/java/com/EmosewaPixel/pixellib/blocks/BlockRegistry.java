package com.EmosewaPixel.pixellib.blocks;

import com.EmosewaPixel.pixellib.PixelLib;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.lists.Materials;
import com.EmosewaPixel.pixellib.materialSystem.lists.ObjTypes;
import com.EmosewaPixel.pixellib.materialSystem.materials.DustMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.RegistryEvent;

//This class is used for generating Blocks for all the possible Material-Object Type combinations
public class BlockRegistry {
    public static void registry(RegistryEvent.Register<Block> e) {
        Materials.getAll().forEach(mat ->
                ObjTypes.getAll().stream()
                        .filter(type -> (mat instanceof DustMaterial && type instanceof BlockType && type.isMaterialCompatible(mat) && !MaterialBlocks.contains(mat, type) && !mat.hasBlacklisted(type)))
                        .forEach(type -> register(new MaterialBlock((DustMaterial) mat, (BlockType) type), e))
        );
    }

    public static void itemRegistry(RegistryEvent.Register<Item> e) {
        MaterialBlocks.getAllBlocks().stream().filter(block -> block instanceof IMaterialItem).forEach(block -> registerItemBlock(block, e));
    }

    private static Block register(Block block, RegistryEvent.Register<Block> e) {
        e.getRegistry().register(block);
        return block;
    }

    private static void registerItemBlock(Block block, RegistryEvent.Register<Item> e) {
        registerItemBlock(block, e, true);
    }

    private static void registerItemBlock(Block block, RegistryEvent.Register<Item> e, boolean withGroup) {
        e.getRegistry().register(new BlockItem(block, withGroup ? new Item.Properties().group(PixelLib.main) : new Item.Properties()) {
            @Override
            public ITextComponent getDisplayName(ItemStack stack) {
                return getBlock().getNameTextComponent();
            }
        }.setRegistryName(block.getRegistryName()));
    }
}