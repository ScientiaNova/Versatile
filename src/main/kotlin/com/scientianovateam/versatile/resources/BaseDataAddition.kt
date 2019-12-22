package com.scientianovateam.versatile.resources

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.materialsystem.addition.BaseObjTypes
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
            val hasMaterialBlock = MaterialBlocks[mat, BaseObjTypes.BLOCK] is IMaterialObject
            val itemType = mat.mainItemType
            if (MaterialBlocks.contains(mat, BaseObjTypes.BLOCK) && itemType != null)
                when (mat.blockCompaction) {
                    BlockCompaction.FROM_2X2 -> {
                        if (hasMaterialBlock)
                            RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, BaseObjTypes.BLOCK]!!.toStack(), "II", "II", 'I', mat.getItemTag(itemType))
                        if (MaterialItems[mat, itemType] is IMaterialObject || hasMaterialBlock)
                            RecipeMaker.addShapelessRecipe(location("${mat}_${itemType}_from_block"), "${mat}_${itemType}", MaterialItems[mat, itemType]!! * 4, mat.getItemTag(BaseObjTypes.BLOCK))
                    }
                    BlockCompaction.FROM_3X3 -> {
                        if (hasMaterialBlock)
                            RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, BaseObjTypes.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getItemTag(itemType))
                        if (MaterialItems[mat, itemType] is IMaterialObject || hasMaterialBlock)
                            RecipeMaker.addShapelessRecipe(location("${mat}_${itemType}_from_block"), "${mat}_${itemType}", MaterialItems[mat, itemType]!! * 9, mat.getItemTag(BaseObjTypes.BLOCK))
                    }
                    else -> {
                    }
                }
            if (mat.isIngotMaterial) {
                if (MaterialItems[mat, BaseObjTypes.INGOT] is IMaterialObject && MaterialItems.contains(mat, BaseObjTypes.NUGGET))
                    RecipeMaker.addShapedRecipe(location("${mat}_ingot_from_nuggets"), "${mat}_ingot", MaterialItems[mat, BaseObjTypes.INGOT]!!.toStack(), "NNN", "NNN", "NNN", 'N', mat.getItemTag(BaseObjTypes.NUGGET))
                if (mat.simpleProcessing)
                    RecipeMaker.addFurnaceRecipe(location("${mat}_ingot"), mat.getItemTag(BaseObjTypes.DUST), MaterialItems[mat, BaseObjTypes.INGOT]!!)
                if (MaterialItems[mat, BaseObjTypes.NUGGET] is IMaterialObject)
                    RecipeMaker.addShapelessRecipe(location("${mat}_nuggets"), MaterialItems[mat, BaseObjTypes.NUGGET]!! * 9, mat.getItemTag(BaseObjTypes.INGOT))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("versatile", name)
}