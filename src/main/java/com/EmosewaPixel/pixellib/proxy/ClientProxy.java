package com.EmosewaPixel.pixellib.proxy;

import com.EmosewaPixel.pixellib.materialSystem.lists.*;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.materials.Material;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialSystem.types.ItemType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTiered;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReaderBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IModProxy {
    @Override
    public void enque(InterModEnqueueEvent e) {
        color();
    }

    @Override
    public void process(InterModProcessEvent e) {
    }

    @SubscribeEvent
    public static void onModelBaking(ModelBakeEvent e) {
        for (Material mat : Materials.getAll())
            for (ObjectType type : ObjTypes.getAll()) {
                if (type instanceof ItemType) {
                    Item item = MaterialItems.getItem(mat, type);
                    if (item instanceof IMaterialItem)
                        e.getModelRegistry().put(new ModelResourceLocation(item.getRegistryName(), "inventory"), e.getModelRegistry().get(new ModelResourceLocation("pixellib:" + ((IMaterialItem) item).getMaterial().getTextureType().toString() + "_" + ((IMaterialItem) item).getObjType().getName(), "inventory")));
                }
                if (type instanceof BlockType) {
                    Block block = MaterialBlocks.getBlock(mat, type);
                    if (block instanceof IMaterialItem) {
                        e.getModelRegistry().put(new ModelResourceLocation(block.getRegistryName(), ""), e.getModelRegistry().get(new ModelResourceLocation("pixellib:" + ((IMaterialItem) block).getMaterial().getTextureType().toString() + "_" + ((IMaterialItem) block).getObjType().getName(), "")));
                        e.getModelRegistry().put(new ModelResourceLocation(block.getRegistryName(), "inventory"), e.getModelRegistry().get(new ModelResourceLocation("pixellib:" + ((IMaterialItem) block).getMaterial().getTextureType().toString() + "_" + ((IMaterialItem) block).getObjType().getName(), "inventory")));
                    }
                }
            }
    }

    private void color() {
        for (Material mat : Materials.getAll())
            for (ObjectType type : ObjTypes.getAll()) {
                if (type instanceof ItemType) {
                    Item item = MaterialItems.getItem(mat, type);
                    if (item instanceof IMaterialItem) {
                        if (!(item instanceof ItemTiered))
                            Minecraft.getInstance().getItemColors().register((ItemStack stack, int index) -> {
                                Item sItem = stack.getItem();
                                if (sItem instanceof IMaterialItem)
                                    return ((IMaterialItem) sItem).getMaterial().getColor();
                                return -1;
                            }, item);

                        if (item instanceof ItemTiered)
                            Minecraft.getInstance().getItemColors().register((ItemStack stack, int index) -> {
                                Item sItem = stack.getItem();
                                if (sItem instanceof IMaterialItem && index == 1)
                                    return ((IMaterialItem) sItem).getMaterial().getColor();
                                return -1;
                            }, item);
                    }
                }

                if (type instanceof BlockType) {
                    Block block = MaterialBlocks.getBlock(mat, type);
                    if (block instanceof IMaterialItem) {
                        Minecraft.getInstance().getBlockColors().register((IBlockState state, @Nullable IWorldReaderBase reader, @Nullable BlockPos pos, int index) -> {
                            Block sBlock = state.getBlock();
                            if (sBlock instanceof IMaterialItem && index == 0)
                                return ((IMaterialItem) sBlock).getMaterial().getColor();
                            return -1;
                        }, block);

                        Minecraft.getInstance().getItemColors().register((ItemStack stack, int index) -> {
                            Block sBlock = Block.getBlockFromItem(stack.getItem());
                            if (sBlock instanceof IMaterialItem && index == 0)
                                return ((IMaterialItem) sBlock).getMaterial().getColor();
                            return -1;
                        }, block.asItem());
                    }
                }
            }
    }
}