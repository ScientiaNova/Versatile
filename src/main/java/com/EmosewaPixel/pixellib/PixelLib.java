package com.EmosewaPixel.pixellib;

import com.EmosewaPixel.pixellib.blocks.BlockRegistry;
import com.EmosewaPixel.pixellib.items.ItemRegistry;
import com.EmosewaPixel.pixellib.materialSystem.materials.MaterialRegistry;
import com.EmosewaPixel.pixellib.proxy.ClientProxy;
import com.EmosewaPixel.pixellib.proxy.IModProxy;
import com.EmosewaPixel.pixellib.proxy.ServerProxy;
import com.EmosewaPixel.pixellib.resourceAddition.DataAddition;
import com.EmosewaPixel.pixellib.tiles.guis.ModGuiHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PixelLib.ModId)
public class PixelLib {
    public static final String ModId = "pixellib";
    public static final Logger LOGGER = LogManager.getLogger();
    private static IModProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public static ItemGroup main = new ItemGroup("pixel_lib") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockRegistry.getGroupIcon());
        }
    };

    public PixelLib() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.GUIFACTORY, () -> ModGuiHandler::guis);

        MinecraftForge.EVENT_BUS.register(this);

        new MaterialRegistry();
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        proxy.enque(event);
    }

    private void processIMC(final InterModProcessEvent event) {
        proxy.process(event);
        DataAddition.register();
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ModId)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlockRegistry(final RegistryEvent.Register<Block> e) {
            BlockRegistry.registry(e);
        }

        @SubscribeEvent
        public static void onItemRegistry(final RegistryEvent.Register<Item> e) {
            ItemRegistry.registry(e);
            BlockRegistry.itemRegistry(e);
        }
    }
}