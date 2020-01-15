package com.scientianovateam.versatile.resources

import com.scientianovateam.versatile.common.extensions.times
import com.scientianovateam.versatile.common.extensions.toStack
import com.scientianovateam.versatile.materialsystem.lists.FORMS
import com.scientianovateam.versatile.materialsystem.lists.MATERIALS
import com.scientianovateam.versatile.materialsystem.lists.MaterialBlocks
import com.scientianovateam.versatile.materialsystem.lists.MaterialItems
import com.scientianovateam.versatile.materialsystem.properties.BlockCompaction
import net.minecraft.util.ResourceLocation
import net.minecraftforge.registries.ForgeRegistries

object BaseDataAddition {
    @JvmStatic
    fun register() {
        //Tags
        FORMS.forEach { form ->
            form.properties.keys.forEach { mat ->
                val registryName = form.registryName(mat)
                ForgeRegistries.ITEMS.getValue(registryName)?.let { item ->
                    TagMaps.addItemToTag(form.itemTag, item)
                    form.combinedItemTags(mat).forEach { TagMaps.addItemToTag(it, item) }
                }
                ForgeRegistries.BLOCKS.getValue(registryName)?.let { block ->
                    TagMaps.addBlockToTag(form.blockTag, block)
                    form.combinedBlockTags(mat).forEach { TagMaps.addBlockToTag(it, block) }
                }
                //TODO fluids
            }
        }

        //Recipes
        MATERIALS.forEach { mat ->
            if (MaterialBlocks.contains(mat, FORMS["storage_block"]!!))
                for (formName in arrayOf("dust", "ingot", "gem")) {
                    val form = FORMS[formName]!!
                    when (mat.blockCompaction) {
                        BlockCompaction.FROM_2X2 -> {
                            RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, FORMS["storage_block"]!!]!!.toStack(), "II", "II", 'I', mat.getItemTag(form))
                            RecipeMaker.addShapelessRecipe(location("${mat}_${formName}_from_block"), "${mat}_${formName}", MaterialItems[mat, form]!! * 4, mat.getItemTag(FORMS["storage_block"]!!))
                        }
                        BlockCompaction.FROM_3X3 -> {
                            RecipeMaker.addShapedRecipe(location("${mat}_block"), MaterialBlocks[mat, FORMS["storage_block"]!!]!!.toStack(), "III", "III", "III", 'I', mat.getItemTag(form))
                            RecipeMaker.addShapelessRecipe(location("${mat}_${formName}_from_block"), "${mat}_${formName}", MaterialItems[mat, form]!! * 9, mat.getItemTag(FORMS["storage_block"]!!))
                        }
                        BlockCompaction.NONE -> {
                        }
                    }
                }
            if (mat.hasIngot) {
                if (MaterialItems.contains(mat, FORMS["nugget"]!!))
                    RecipeMaker.addShapedRecipe(location("${mat}_ingot_from_nuggets"), "${mat}_ingot", MaterialItems[mat, FORMS["ingot"]!!]!!.toStack(), "NNN", "NNN", "NNN", 'N', mat.getItemTag(FORMS["ingot"]!!))
                if (mat.simpleProcessing)
                    RecipeMaker.addFurnaceRecipe(location("${mat}_ingot"), mat.getItemTag(FORMS["dust"]!!), MaterialItems[mat, FORMS["ingot"]!!]!!)
                RecipeMaker.addShapelessRecipe(location("${mat}_nuggets"), MaterialItems[mat, FORMS["nugget"]!!]!! * 9, mat.getItemTag(FORMS["ingot"]!!))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("versatile", name)
}