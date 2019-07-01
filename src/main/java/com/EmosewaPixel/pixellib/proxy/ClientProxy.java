package com.EmosewaPixel.pixellib.proxy;

import com.EmosewaPixel.pixellib.items.MaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.MaterialRegistry;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialBlocks;
import com.EmosewaPixel.pixellib.materialSystem.lists.MaterialItems;
import com.EmosewaPixel.pixellib.materialSystem.materials.DustMaterial;
import com.EmosewaPixel.pixellib.materialSystem.materials.IMaterialItem;
import com.EmosewaPixel.pixellib.materialSystem.types.BlockType;
import com.EmosewaPixel.pixellib.materialSystem.types.ObjectType;
import com.EmosewaPixel.pixellib.resourceAddition.FakeResourcePackFinder;
import com.EmosewaPixel.pixellib.resourceAddition.JSONAdder;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IModProxy {
    @Override
    public void init() {
        Minecraft.getInstance().getResourcePackList().addPackFinder(new FakeResourcePackFinder());
    }

    public static void addModelJsons() {
        MaterialItems.getAllItems().stream().filter(i -> i instanceof IMaterialItem).forEach(i -> {
            ResourceLocation registryName = i.getRegistryName();
            ObjectType type = ((IMaterialItem) i).getObjType();
            JsonObject model = new JsonObject();
            model.addProperty("parent", registryName.getNamespace() + ":item/materialitems/" + (type.hasTag(MaterialRegistry.SINGLE_TEXTURE_TYPE) ? type.getName() : ((IMaterialItem) i).getMaterial().getTextureType().toString() + "/" + type.getName()));
            JSONAdder.addAssetsJSON(new ResourceLocation(registryName.getNamespace(), "models/item/" + registryName.getPath() + ".json"), model);
        });

        MaterialBlocks.getAllBlocks().stream().filter(b -> b instanceof IMaterialItem).forEach(b -> {
            ResourceLocation registryName = b.getRegistryName();
            ObjectType type = ((IMaterialItem) b).getObjType();
            JsonObject model = new JsonObject();
            model.addProperty("parent", registryName.getNamespace() + ":block/materialblocks/" + (type.hasTag(MaterialRegistry.SINGLE_TEXTURE_TYPE) ? type.getName() : ((IMaterialItem) b).getMaterial().getTextureType().toString() + "/" + type.getName()));
            JSONAdder.addAssetsJSON(new ResourceLocation(registryName.getNamespace(), "models/item/" + registryName.getPath() + ".json"), model);
            JSONAdder.addAssetsJSON(new ResourceLocation(registryName.getNamespace(), "blockstates/" + registryName.getPath() + ".json"), ((BlockType) type).getBlockstateJson((IMaterialItem) b));
        });
    }

    @Override
    public void enque(InterModEnqueueEvent e) {
        color();
    }

    @Override
    public void process(InterModProcessEvent e) {
    }

    private void color() {
        MaterialItems.getAllItems().stream().filter(item -> item instanceof MaterialItem).forEach(item ->
                Minecraft.getInstance().getItemColors().register((ItemStack stack, int index) -> {
                    Item sItem = stack.getItem();
                    if (sItem instanceof IMaterialItem)
                        if (((IMaterialItem) sItem).getObjType().hasTag(MaterialRegistry.USES_UNREFINED_COLOR) && ((IMaterialItem) sItem).getMaterial() instanceof DustMaterial)
                            return ((DustMaterial) ((IMaterialItem) sItem).getMaterial()).getUnrefinedColor();
                        else
                            return ((IMaterialItem) sItem).getMaterial().getColor();
                    return -1;
                }, item)
        );

        MaterialBlocks.getAllBlocks().stream().filter(block -> block instanceof IMaterialItem).forEach(block -> {
            Minecraft.getInstance().getBlockColors().register((BlockState state, @Nullable IEnviromentBlockReader reader, @Nullable BlockPos pos, int index) -> {
                Block sBlock = state.getBlock();
                if (sBlock instanceof IMaterialItem && index == 0)
                    if (((IMaterialItem) sBlock).getObjType().hasTag(MaterialRegistry.USES_UNREFINED_COLOR) && ((IMaterialItem) sBlock).getMaterial() instanceof DustMaterial)
                        return ((DustMaterial) ((IMaterialItem) sBlock).getMaterial()).getUnrefinedColor();
                    else
                        return ((IMaterialItem) sBlock).getMaterial().getColor();
                return -1;
            }, block);

            Minecraft.getInstance().getItemColors().register((ItemStack stack, int index) -> {
                Block sBlock = Block.getBlockFromItem(stack.getItem());
                if (sBlock instanceof IMaterialItem && index == 0)
                    if (((IMaterialItem) sBlock).getObjType().hasTag(MaterialRegistry.USES_UNREFINED_COLOR) && ((IMaterialItem) sBlock).getMaterial() instanceof DustMaterial)
                        return ((DustMaterial) ((IMaterialItem) sBlock).getMaterial()).getUnrefinedColor();
                    else
                        return ((IMaterialItem) sBlock).getMaterial().getColor();
                return -1;
            }, block.asItem());
        });
    }
}