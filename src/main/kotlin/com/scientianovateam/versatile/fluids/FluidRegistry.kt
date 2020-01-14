package com.scientianovateam.versatile.fluids

import com.scientianovateam.versatile.materialsystem.lists.FORMS
import com.scientianovateam.versatile.materialsystem.lists.MATERIALS
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import net.minecraft.fluid.Fluid
import net.minecraftforge.event.RegistryEvent

object FluidRegistry {
    fun registerFluids(e: RegistryEvent.Register<Fluid>) {
        MaterialFluids.additionSuppliers.cellSet()
                .forEach { MaterialFluids.addFluidPair(it.rowKey!!, it.columnKey!!, it.value!!()) }
        MATERIALS.forEach { mat ->
            FORMS.filter { form -> form.isMaterialCompatible(mat) && !MaterialFluids.contains(mat, form) && mat.invertedBlacklist != (form.name in mat.formBlacklist) }
                    .forEach { form ->
                        //TODO fluids
                    }
        }
    }
}