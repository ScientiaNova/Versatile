package com.emosewapixel.pixellib.machines

import com.emosewapixel.pixellib.machines.gui.BaseContainer
import net.alexwells.kottle.KotlinEventBusSubscriber
import net.minecraft.inventory.container.ContainerType
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import java.util.function.Supplier

@KotlinEventBusSubscriber(bus = KotlinEventBusSubscriber.Bus.MOD)
object BaseMachineRegistry {
    val BASE_CONTAINER: ContainerType<BaseContainer> = IForgeContainerType.create(::BaseContainer)
    val BASE_TILE_ENTITY: TileEntityType<BaseTileEntity> = TileEntityType.Builder.create(Supplier { BaseTileEntity() }, *BaseTileEntity.USED_BY.toTypedArray()).build(null)

    @SubscribeEvent
    fun onContainerRegistry(event: RegistryEvent.Register<ContainerType<*>>) {
        event.registry.register(BASE_CONTAINER.setRegistryName("pixellib:base_container"))
    }

    @SubscribeEvent
    fun onTERegistry(event: RegistryEvent.Register<TileEntityType<*>>) {
        event.registry.register(BASE_TILE_ENTITY.setRegistryName("pixellib:base_te"))
    }
}