package com.scientianovateam.versatile.fluids

import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.lists.ObjTypes
import net.minecraft.fluid.Fluid
import net.minecraftforge.event.RegistryEvent

object FluidRegistry {
    fun registerFluids(e: RegistryEvent.Register<Fluid>) {
        MaterialFluids.additionSuppliers.cellSet()
                .forEach { MaterialFluids.addFluidPair(it.rowKey!!, it.columnKey!!, it.value!!()) }
        Materials.all.forEach { mat ->
            ObjTypes.all.filter { type -> type.isMaterialCompatible(mat) && !MaterialFluids.contains(mat, type) && if (mat.invertedBlacklist) type in mat.typeBlacklist else type !in mat.typeBlacklist }
                    .forEach { type ->
                        type.fluidPairConstructor?.invoke(mat)?.let {
                            e.registry.register(it.still)
                            e.registry.register(it.flowing)
                        }
                    }
        }
    }
}