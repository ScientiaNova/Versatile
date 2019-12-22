package com.scientianovateam.versatile.materialsystem.lists

import com.scientianovateam.versatile.fluids.IFluidPairHolder
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.main.ObjectType
import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import net.minecraft.fluid.FlowingFluid
import net.minecraft.fluid.Fluid

object MaterialFluids {
    private val materialFluids = HashBasedTable.create<Material, ObjectType, IFluidPairHolder>()
    val additionSuppliers: HashBasedTable<Material, ObjectType, () -> IFluidPairHolder> = HashBasedTable.create<Material, ObjectType, () -> IFluidPairHolder>()

    @JvmStatic
    val all
        get() = allPairs.map { it.still }

    @JvmStatic
    val allPairs: Collection<IFluidPairHolder>
        get() = materialFluids.values()

    @JvmStatic
    fun getPair(material: Material, type: ObjectType): IFluidPairHolder? = materialFluids.get(material, type)

    @JvmStatic
    fun getPair(material: Material): MutableMap<ObjectType, IFluidPairHolder>? = materialFluids.row(material)

    @JvmStatic
    fun getPair(type: ObjectType): MutableMap<Material, IFluidPairHolder>? = materialFluids.column(type)

    @JvmStatic
    operator fun get(material: Material, type: ObjectType): FlowingFluid? = getPair(material, type)?.still

    @JvmStatic
    operator fun get(material: Material): Map<ObjectType, FlowingFluid>? = getPair(material)?.mapValues { (_, v) -> v.still }

    @JvmStatic
    operator fun get(type: ObjectType): Map<Material, FlowingFluid>? = getPair(type)?.mapValues { (_, v) -> v.still }

    @JvmStatic
    fun contains(material: Material, type: ObjectType) = materialFluids.contains(material, type)

    @JvmStatic
    operator fun contains(fluid: Fluid) = fluid in (all + allPairs.map { it.flowing })

    @JvmStatic
    fun addFluidPair(mat: Material, type: ObjectType, fluidPair: IFluidPairHolder) {
        materialFluids.put(mat, type, fluidPair)
        MaterialItems.addItem(mat, type, fluidPair.still.filledBucket)
        MaterialBlocks.addBlock(mat, type, fluidPair.still.defaultState.blockState.block)
    }

    @JvmStatic
    fun addFluidPair(mat: Material, type: ObjectType, fluidPair: () -> IFluidPairHolder) = additionSuppliers.put(mat, type, fluidPair)

    @JvmStatic
    fun <O> addFluidPair(fluidPair: O) where O : IMaterialObject, O : IFluidPairHolder = materialFluids.put(fluidPair.mat, fluidPair.objType, fluidPair)

    @JvmStatic
    fun getFluidCell(fluid: FlowingFluid): Table.Cell<Material, ObjectType, IFluidPairHolder>? = materialFluids.cellSet().firstOrNull { it.value?.still === fluid }

    @JvmStatic
    fun getFluidMaterial(fluid: FlowingFluid): Material? = if (fluid is IMaterialObject) fluid.mat else getFluidCell(fluid)?.rowKey

    @JvmStatic
    fun getFluidObjType(fluid: FlowingFluid): ObjectType? = if (fluid is IMaterialObject) fluid.objType else getFluidCell(fluid)?.columnKey
}