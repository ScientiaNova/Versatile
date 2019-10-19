package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.extensions.times
import com.emosewapixel.pixellib.extensions.toStack
import com.emosewapixel.pixellib.materialsystem.addition.PLMaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.GemMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.IngotMaterial
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

//This claass is used for registering the default data added by Pixel Lib
object BaseDataAddition {
    @JvmStatic
    fun register() {
        //Tags
        MaterialItems.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        MaterialBlocks.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        MaterialFluids.all.flatMap { listOf(it.stillFluid, it.flowingFluid) }.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        TagMaps.addItemToTag("forge:gems/coal", Items.COAL)
        TagMaps.addItemToTag("forge:gems/charcoal", Items.CHARCOAL)
        TagMaps.addItemToTag("forge:gems", Items.COAL)
        TagMaps.addItemToTag("forge:gems", Items.CHARCOAL)
        TagMaps.addBlockToTag("forge:storage_blocks/brick", Blocks.BRICKS)
        TagMaps.addBlockToTag("forge:storage_blocks/nether_brick", Blocks.NETHER_BRICKS)
        TagMaps.addBlockToTag("forge:storage_blocks/bone", Blocks.BONE_BLOCK)
        TagMaps.addBlockToTag("forge:storage_blocks", Blocks.BRICKS)
        TagMaps.addBlockToTag("forge:storage_blocks", Blocks.NETHER_BRICKS)
        TagMaps.addBlockToTag("forge:storage_blocks", Blocks.BONE_BLOCK)
        TagMaps.addItemToTag("forge:dusts/bone", Items.BONE_MEAL)
        TagMaps.addItemToTag("forge:dusts/blaze", Items.BLAZE_POWDER)
        TagMaps.addItemToTag("forge:dusts", Items.BONE_MEAL)
        TagMaps.addItemToTag("forge:dusts", Items.BLAZE_POWDER)

        //Recipes
        Materials.all.filterIsInstance<DustMaterial>().forEach { mat ->
            val hasMaterialBlock = MaterialBlocks[mat, PLMaterialRegistry.BLOCK] is IMaterialObject
            if (mat.javaClass == DustMaterial::class.java)
                if (mat.hasTag(PLMaterialRegistry.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, PLMaterialRegistry.BLOCK]!!.toStack(), "II", "II", 'I', mat.getTag(PLMaterialRegistry.DUST))
                    if (MaterialItems[mat, PLMaterialRegistry.DUST] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_dust_from_block"), mat.name + "_dust", MaterialItems[mat, PLMaterialRegistry.DUST]!! * 4, mat.getTag(PLMaterialRegistry.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_dust"), MaterialBlocks[mat, PLMaterialRegistry.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getTag(PLMaterialRegistry.DUST))
                    if (MaterialItems[mat, PLMaterialRegistry.DUST] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_dust_from_block"), mat.name + "_dust", MaterialItems[mat, PLMaterialRegistry.DUST]!! * 9, mat.getTag(PLMaterialRegistry.BLOCK))
                }
            if (mat is GemMaterial)
                if (mat.hasTag(PLMaterialRegistry.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, PLMaterialRegistry.BLOCK]!!.toStack(), "II", "II", 'I', mat.getTag(PLMaterialRegistry.GEM))
                    if (MaterialItems[mat, PLMaterialRegistry.GEM] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_gem_from_block"), mat.name + "_gem", MaterialItems[mat, PLMaterialRegistry.GEM]!! * 4, mat.getTag(PLMaterialRegistry.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, PLMaterialRegistry.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getTag(PLMaterialRegistry.GEM))
                    if (MaterialItems[mat, PLMaterialRegistry.GEM] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_gem_from_block"), mat.name + "_gem", MaterialItems[mat, PLMaterialRegistry.GEM]!! * 9, mat.getTag(PLMaterialRegistry.BLOCK))
                }
            if (mat is IngotMaterial) {
                if (mat.hasTag(PLMaterialRegistry.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, PLMaterialRegistry.BLOCK]!!.toStack(), "II", "II", 'I', mat.getTag(PLMaterialRegistry.INGOT))
                    if (MaterialItems[mat, PLMaterialRegistry.INGOT] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_ingot_from_block"), mat.name + "_ingot", MaterialItems[mat, PLMaterialRegistry.INGOT]!! * 4, mat.getTag(PLMaterialRegistry.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, PLMaterialRegistry.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getTag(PLMaterialRegistry.INGOT))
                    if (MaterialItems[mat, PLMaterialRegistry.INGOT] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_ingot_from_block"), mat.name + "_ingot", MaterialItems[mat, PLMaterialRegistry.INGOT]!! * 9, mat.getTag(PLMaterialRegistry.BLOCK))
                }
                if (MaterialItems[mat, PLMaterialRegistry.INGOT] is IMaterialObject && MaterialItems.contains(mat, PLMaterialRegistry.NUGGET))
                    RecipeMaker.addShapedRecipe(location(mat.name + "ingot_from_nuggets"), mat.name + "_ingot", MaterialItems[mat, PLMaterialRegistry.INGOT]!!.toStack(), "NNN", "NNN", "NNN", 'N', mat.getTag(PLMaterialRegistry.NUGGET))
                if (!mat.hasTag(PLMaterialRegistry.DISABLE_SIMPLE_PROCESSING))
                    RecipeMaker.addFurnaceRecipe(location(mat.name + "_ingot"), mat.getTag(PLMaterialRegistry.DUST), MaterialItems[mat, PLMaterialRegistry.INGOT]!!)
                if (MaterialItems[mat, PLMaterialRegistry.NUGGET] is IMaterialObject)
                    RecipeMaker.addShapelessRecipe(location(mat.name + "_nuggets"), MaterialItems[mat, PLMaterialRegistry.NUGGET]!! * 9, mat.getTag(PLMaterialRegistry.INGOT))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("pixellib", name)
}