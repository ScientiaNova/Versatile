package com.emosewapixel.pixellib;

import com.emosewapixel.pixellib.blocks.BlockRegistry;
import com.emosewapixel.pixellib.commands.FluidContainerCommand;
import com.emosewapixel.pixellib.commands.MaterialCommand;
import com.emosewapixel.pixellib.commands.ObjTypeCommand;
import com.emosewapixel.pixellib.fluids.FluidRegistry;
import com.emosewapixel.pixellib.items.ItemRegistry;
import com.emosewapixel.pixellib.items.MaterialItem;
import com.emosewapixel.pixellib.machines.packets.NetworkHandler;
import com.emosewapixel.pixellib.materialsystem.addition.MaterialRegistryInitializer;
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials;
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems;
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject;
import com.emosewapixel.pixellib.materialsystem.types.FluidType;
import com.emosewapixel.pixellib.proxy.ClientProxy;
import com.emosewapixel.pixellib.proxy.ClientProxyKt;
import com.emosewapixel.pixellib.proxy.IModProxy;
import com.emosewapixel.pixellib.proxy.ServerProxy;
import com.emosewapixel.pixellib.resources.BaseDataAddition;
import com.emosewapixel.pixellib.resources.FakeDataPackFinder;
import com.emosewapixel.pixellib.worldgen.OreGen;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(PixelLib.ModId)
public final class PixelLib {
	public static final String ModId = "pixellib";
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private IModProxy proxy = DistExecutor.runForDist(() -> () -> ClientProxy.INSTANCE, () -> () -> ServerProxy.INSTANCE);
	
	public static final ItemGroup MAIN = new ItemGroup(ModId) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(MaterialItems.getAll().stream().filter(item -> item instanceof MaterialItem).findFirst().get());
		}
	};
	
	public PixelLib() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		
		proxy.init();
	}
	
	private void commonSetup(FMLCommonSetupEvent e) {
		NetworkHandler.INSTANCE.getCHANNEL();
	}
	
	private void enqueueIMC(InterModEnqueueEvent e) {
		proxy.enque(e);
		OreGen.register();
	}
	
	private void processIMC(InterModProcessEvent e) {
		proxy.process(e);
		BaseDataAddition.register();
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PixelLib.ModId)
	public static final class RegistryEvents {
		@SubscribeEvent(priority = EventPriority.HIGHEST)
		public static void onEarlyBlockRegistry(RegistryEvent.Register<Block> e) {
			MaterialRegistryInitializer.INSTANCE.getInstances();
		}
		
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onLateBlockRegistry(RegistryEvent.Register<Block> e) {
			BlockRegistry.INSTANCE.registerBlocks(e);
			FluidRegistry.INSTANCE.registerBlocks(e);
		}
		
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onLateItemRegistry(RegistryEvent.Register<Item> e) {
			ItemRegistry.INSTANCE.registerItems(e);
			BlockRegistry.INSTANCE.registerItems(e);
			FluidRegistry.INSTANCE.registerItems(e);
			ClientProxyKt.addModelJSONs();
		}
		
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void onLateFluidRegistry(RegistryEvent.Register<Fluid> e) {
			FluidRegistry.INSTANCE.registerFluids(e);
		}
	}
	
	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = PixelLib.ModId)
	public static final class GameEvents {
		@SubscribeEvent(priority = EventPriority.HIGH)
		public static void onServerAboutToStart(FMLServerAboutToStartEvent e) {
			e.getServer().getResourcePacks().addPackFinder(FakeDataPackFinder.INSTANCE);
		}
		
		@SubscribeEvent
		public static void onServerStart(FMLServerStartingEvent e) {
			new MaterialCommand(e.getCommandDispatcher());
			new ObjTypeCommand(e.getCommandDispatcher());
			new FluidContainerCommand(e.getCommandDispatcher());
		}
		
		@SubscribeEvent
		public static void fuelTime(FurnaceFuelBurnTimeEvent e) {
			Item item = e.getItemStack().getItem();
			if (item instanceof IMaterialObject)
				if (!((IMaterialObject) item).getObjType().hasTag(BaseMaterials.HAS_NO_FUEL_VALUE) && ((IMaterialObject) item).getObjType().getBucketVolume() != 0)
					e.setBurnTime(((IMaterialObject) item).getObjType() instanceof FluidType ? ((IMaterialObject) item).getObjType().getBucketVolume() / 1000 : ((IMaterialObject) item).getObjType().getBucketVolume() / 144 * ((IMaterialObject) item).getMat().getStandardBurnTime());
		}
	}
}