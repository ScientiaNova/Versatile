package com.scientianovateam.versatile.resources

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.materialsystem.addition.BaseForms
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialFluids
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.lists.Materials
import com.scientianovateam.versatile.materialsystem.main.IMaterialObject
import com.scientianovateam.versatile.materialsystem.main.Material
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
        Materials.all.filter(Material::isItemMaterial).forEach { mat ->
            val hasMaterialBlock = MaterialBlocks[mat, BaseForms.BLOCK] is IMaterialObject
            val itemType = mat.mainItemType
            if (MaterialBlocks.contains(mat, BaseForms.BLOCK) && itemType != null)
                when (mat.blockCompaction) {
                    BlockCompaction.FROM_2X2 -> {
                        if (hasMaterialBlock)
                            RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, BaseForms.BLOCK]!!.toStack(), "II", "II", 'I', mat.getItemTag(itemType))
                        if (MaterialItems[mat, itemType] is IMaterialObject || hasMaterialBlock)
                            RecipeMaker.addShapelessRecipe(location("${mat}_${itemType}_from_block"), "${mat}_${itemType}", MaterialItems[mat, itemType]!! * 4, mat.getItemTag(BaseForms.BLOCK))
                    }
                    BlockCompaction.FROM_3X3 -> {
                        if (hasMaterialBlock)
                            RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, BaseForms.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getItemTag(itemType))
                        if (MaterialItems[mat, itemType] is IMaterialObject || hasMaterialBlock)
                            RecipeMaker.addShapelessRecipe(location("${mat}_${itemType}_from_block"), "${mat}_${itemType}", MaterialItems[mat, itemType]!! * 9, mat.getItemTag(BaseForms.BLOCK))
                    }
                    else -> {
                    }
                }
            if (mat.isIngotMaterial) {
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