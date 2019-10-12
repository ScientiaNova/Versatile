package com.emosewapixel.pixellib.materialsystem.materials.utility.ct

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import com.emosewapixel.pixellib.materialsystem.materials.Material
import net.minecraft.block.Block
import net.minecraft.item.Item
import java.util.function.Supplier

@ZenRegister
@FunctionalInterface
interface MaterialSupplier : Supplier<Material>

@ZenRegister
@FunctionalInterface
interface ItemSupplier : Supplier<Item>

@ZenRegister
@FunctionalInterface
interface BlockSupplier : Supplier<Block>

@ZenRegister
@FunctionalInterface
interface FluidPairSupplier : Supplier<IFluidPairHolder>