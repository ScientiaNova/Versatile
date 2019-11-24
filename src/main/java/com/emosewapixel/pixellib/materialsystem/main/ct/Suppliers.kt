package com.emosewapixel.pixellib.materialsystem.main.ct

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.fluids.IFluidPairHolder
import net.minecraft.block.Block
import net.minecraft.item.Item
import java.util.function.Supplier

@ZenRegister
@FunctionalInterface
interface ItemSupplier : Supplier<Item>

@ZenRegister
@FunctionalInterface
interface BlockSupplier : Supplier<Block>

@ZenRegister
@FunctionalInterface
interface FluidPairSupplier : Supplier<IFluidPairHolder>