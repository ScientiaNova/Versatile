package com.emosewapixel.pixellib.fluids

import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.lists.ObjTypes
import com.emosewapixel.pixellib.materialsystem.types.FluidType
import net.minecraft.block.Block
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

object FluidRegistry {
    private val items = mutableListOf<Item>()
    private val fluids = mutableListOf<Fluid>()

    fun registerBlocks(e: RegistryEvent.Register<Block>) {
        Materials.all.forEach { mat ->
            ObjTypes.all.filter { type -> type is FluidType && type.isMaterialCompatible(mat) && !MaterialBlocks.contains(mat, type) && type !in mat.typeBlacklist }
                    .forEach { type ->
                        e.registry.register((type as FluidType).blockConstructor(mat, type))
                        items += type.bucketConstructor(mat, type)
                        val fluidPair = type.objectConstructor(mat, type)
                        fluids += fluidPair.still
                        fluids += fluidPair.flowing
                    }
        }
    }

    fun registerItems(e: RegistryEvent.Register<Item>) = items.forEach { e.registry.register(it) }

    fun registerFluids(e: RegistryEvent.Register<Fluid>) = fluids.forEach { e.registry.register(it) }
}