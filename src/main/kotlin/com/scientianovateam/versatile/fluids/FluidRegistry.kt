package com.scientianovateam.versatile.fluids

import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.lists.Forms
import net.minecraft.fluid.Fluid
import net.minecraftforge.event.RegistryEvent

object FluidRegistry {
    fun registerFluids(e: RegistryEvent.Register<Fluid>) {
        MaterialFluids.additionSuppliers.cellSet()
                .forEach { MaterialFluids.addFluidPair(it.rowKey!!, it.columnKey!!, it.value!!()) }
        Materials.all.forEach { mat ->
            Forms.all.filter { form -> form.isMaterialCompatible(mat) && !MaterialFluids.contains(mat, form) && mat.invertedBlacklist != (form.name in mat.formBlacklist) }
                    .forEach { form ->
                        form.fluidPairConstructor?.invoke(mat)?.let {
                            e.registry.register(it.still)
                            e.registry.register(it.flowing)
                        }
                    }
        }
    }
}