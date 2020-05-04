package com.scientianova.versatile.machines

import com.scientianovateam.versatile.machines.gui.BaseContainer
import net.minecraft.inventory.container.ContainerType
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import java.util.function.Supplier

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object BaseMachineRegistry {
    lateinit var BASE_CONTAINER: ContainerType<BaseContainer>
        private set
    lateinit var BASE_TILE_ENTITY: TileEntityType<BaseTileEntity>
        private set

    @SubscribeEvent
    fun onContainerRegistry(event: RegistryEvent.Register<ContainerType<*>>) {
        BASE_CONTAINER = IForgeContainerType.create(::BaseContainer)
        event.registry.register(BASE_CONTAINER.setRegistryName("versatile:base_container"))
    }

    @SubscribeEvent
    fun onTERegistry(event: RegistryEvent.Register<TileEntityType<*>>) {
        BASE_TILE_ENTITY = TileEntityType.Builder.create(Supplier { BaseTileEntity() }, *BaseTileEntity.USED_BY.toTypedArray()).build(null)
        event.registry.register(BASE_TILE_ENTITY.setRegistryName("versatile:base_te"))
    }
}