package com.scientianovateam.versatile.resources

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.materialsystem.addition.BaseForms
import com.scientianovateam.versatile.materialsystem.lists.*
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import net.minecraft.util.ResourceLocation

object BaseDataAddition {
    @JvmStatic
    fun register() {
        //Tags
        MaterialItems.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        MaterialBlocks.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        MaterialFluids.all.flatMap { listOf(it.stillFluid, it.flowingFluid) }.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }

        //Recipes
        Materials.all.forEach { mat ->
            val hasMaterialBlock = MaterialBlocks[mat, BaseForms.BLOCK] is IMaterialObject
            if (MaterialBlocks.contains(mat, BaseForms.BLOCK))
                for (formName in arrayOf("dust", "ingot", "gem")) {
                    val form = Forms[formName]!!
                    when (mat.blockCompaction) {
                        BlockCompaction.FROM_2X2 -> {
                            if (hasMaterialBlock)
                                RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, BaseForms.BLOCK]!!.toStack(), "II", "II", 'I', mat.getItemTag(form))
                            if (MaterialItems[mat, form] is IMaterialObject || hasMaterialBlock)
                                RecipeMaker.addShapelessRecipe(location("${mat}_${formName}_from_block"), "${mat}_${formName}", MaterialItems[mat, form]!! * 4, mat.getItemTag(BaseForms.BLOCK))
                        }
                        BlockCompaction.FROM_3X3 -> {
                            if (hasMaterialBlock)
                                RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, BaseForms.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getItemTag(form))
                            if (MaterialItems[mat, form] is IMaterialObject || hasMaterialBlock)
                                RecipeMaker.addShapelessRecipe(location("${mat}_${formName}_from_block"), "${mat}_${formName}", MaterialItems[mat, form]!! * 9, mat.getItemTag(BaseForms.BLOCK))
                        }
                        BlockCompaction.NONE -> {
                        }
                    }
                }
            if (mat.hasIngot) {
                if (MaterialItems[mat, BaseForms.INGOT] is IMaterialObject && MaterialItems.contains(mat, BaseForms.NUGGET))
                    RecipeMaker.addShapedRecipe(location("${mat}_ingot_from_nuggets"), "${mat}_ingot", MaterialItems[mat, BaseForms.INGOT]!!.toStack(), "NNN", "NNN", "NNN", 'N', mat.getItemTag(BaseForms.NUGGET))
                if (mat.simpleProcessing)
                    RecipeMaker.addFurnaceRecipe(location("${mat}_ingot"), mat.getItemTag(BaseForms.DUST), MaterialItems[mat, BaseForms.INGOT]!!)
                if (MaterialItems[mat, BaseForms.NUGGET] is IMaterialObject)
                    RecipeMaker.addShapelessRecipe(location("${mat}_nuggets"), MaterialItems[mat, BaseForms.NUGGET]!! * 9, mat.getItemTag(BaseForms.INGOT))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("versatile", name)
}