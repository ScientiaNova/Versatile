package com.scientianova.versatile.materialsystem.lists

import com.scientianova.versatile.fluids.IFluidPairHolder
import com.scientianova.versatile.materialsystem.main.IMaterialObject
import com.scientianova.versatile.materialsystem.main.Material
import com.scientianova.versatile.materialsystem.main.Form
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid

object MaterialFluids {
    private val materialFluids = HashBasedTable.create<Material, Form, IFluidPairHolder>()
    val additionSuppliers: HashBasedTable<Material, Form, () -> IFluidPairHolder> = HashBasedTable.create<Material, Form, () -> IFluidPairHolder>()

    @JvmStatic
    val all
        get() = allPairs.map { it.still }

    @JvmStatic
    val allPairs: Collection<IFluidPairHolder>
        get() = materialFluids.values()

    @JvmStatic
    fun getPair(material: Material, type: Form): IFluidPairHolder? = materialFluids.get(material, type)

    @JvmStatic
    fun getPair(material: Material): MutableMap<Form, IFluidPairHolder>? = materialFluids.row(material)

    @JvmStatic
    fun getPair(type: Form): MutableMap<Material, IFluidPairHolder>? = materialFluids.column(type)

    @JvmStatic
    operator fun get(material: Material, type: Form): FlowingFluid? = getPair(material, type)?.still

    @JvmStatic
    operator fun get(material: Material): Map<Form, FlowingFluid>? = getPair(material)?.mapValues { (_, v) -> v.still }

    @JvmStatic
    operator fun get(type: Form): Map<Material, FlowingFluid>? = getPair(type)?.mapValues { (_, v) -> v.still }

    @JvmStatic
    fun contains(material: Material, type: Form) = materialFluids.contains(material, type)

    @JvmStatic
    operator fun contains(fluid: Fluid) = fluid in (all + allPairs.map { it.flowing })

    @JvmStatic
    fun addFluidPair(mat: Material, type: Form, fluidPair: IFluidPairHolder) {
        materialFluids.put(mat, type, fluidPair)
        MaterialItems.addItem(mat, type, fluidPair.still.filledBucket)
        MaterialBlocks.addBlock(mat, type, fluidPair.still.defaultState.blockState.block)
    }

    @JvmStatic
    fun addFluidPair(mat: Material, type: Form, fluidPair: () -> IFluidPairHolder) = additionSuppliers.put(mat, type, fluidPair)

    @JvmStatic
    fun <O> addFluidPair(fluidPair: O) where O : IMaterialObject, O : IFluidPairHolder = materialFluids.put(fluidPair.mat, fluidPair.form, fluidPair)

    @JvmStatic
    fun getFluidCell(fluid: FlowingFluid): Table.Cell<Material, Form, IFluidPairHolder>? = materialFluids.cellSet().firstOrNull { it.value?.still === fluid }

    @JvmStatic
    fun getFluidMaterial(fluid: FlowingFluid): Material? = if (fluid is IMaterialObject) fluid.mat else getFluidCell(fluid)?.rowKey

    @JvmStatic
    fun getFluidObjType(fluid: FlowingFluid): Form? = if (fluid is IMaterialObject) fluid.form else getFluidCell(fluid)?.columnKey
}