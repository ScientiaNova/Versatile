package com.scientianovateam.versatile.resources

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toStack
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
        MATERIALS.forEach { mat ->
            val hasMaterialBlock = MaterialBlocks[mat, FORMS["storage_block"]!!] is IMaterialObject
            if (MaterialBlocks.contains(mat, FORMS["storage_block"]!!))
                for (formName in arrayOf("dust", "ingot", "gem")) {
                    val form = FORMS[formName]!!
                    when (mat.blockCompaction) {
                        BlockCompaction.FROM_2X2 -> {
                            if (hasMaterialBlock)
                                RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, FORMS["storage_block"]!!]!!.toStack(), "II", "II", 'I', mat.getItemTag(form))
                            if (MaterialItems[mat, form] is IMaterialObject || hasMaterialBlock)
                                RecipeMaker.addShapelessRecipe(location("${mat}_${formName}_from_block"), "${mat}_${formName}", MaterialItems[mat, form]!! * 4, mat.getItemTag(FORMS["storage_block"]!!))
                        }
                        BlockCompaction.FROM_3X3 -> {
                            if (hasMaterialBlock)
                                RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, FORMS["storage_block"]!!]!!.toStack(), "III", "III", "III", 'I', mat.getItemTag(form))
                            if (MaterialItems[mat, form] is IMaterialObject || hasMaterialBlock)
                                RecipeMaker.addShapelessRecipe(location("${mat}_${formName}_from_block"), "${mat}_${formName}", MaterialItems[mat, form]!! * 9, mat.getItemTag(FORMS["storage_block"]!!))
                        }
                        BlockCompaction.NONE -> {
                        }
                    }
                }
            if (mat.hasIngot) {
                if (MaterialItems[mat, FORMS["ingot"]!!] is IMaterialObject && MaterialItems.contains(mat, FORMS["nugget"]!!))
                    RecipeMaker.addShapedRecipe(location("${mat}_ingot_from_nuggets"), "${mat}_ingot", MaterialItems[mat, FORMS["ingot"]!!]!!.toStack(), "NNN", "NNN", "NNN", 'N', mat.getItemTag(FORMS["ingot"]!!))
                if (mat.simpleProcessing)
                    RecipeMaker.addFurnaceRecipe(location("${mat}_ingot"), mat.getItemTag(FORMS["dust"]!!), MaterialItems[mat, FORMS["ingot"]!!]!!)
                if (MaterialItems[mat, FORMS["nugget"]!!] is IMaterialObject)
                    RecipeMaker.addShapelessRecipe(location("${mat}_nuggets"), MaterialItems[mat, FORMS["nugget"]!!]!! * 9, mat.getItemTag(FORMS["ingot"]!!))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("versatile", name)
}