package com.emosewapixel.pixellib.resources

import com.emosewapixel.ktlib.extensions.times
import com.emosewapixel.ktlib.extensions.toStack
import com.emosewapixel.pixellib.materialsystem.addition.BaseMaterials
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
            val hasMaterialBlock = MaterialBlocks[mat, BaseMaterials.BLOCK] is IMaterialObject
            if (mat.javaClass == DustMaterial::class.java)
                if (mat.hasTag(BaseMaterials.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, BaseMaterials.BLOCK]!!.toStack(), "II", "II", 'I', mat.getTag(BaseMaterials.DUST))
                    if (MaterialItems[mat, BaseMaterials.DUST] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_dust_from_block"), mat.name + "_dust", MaterialItems[mat, BaseMaterials.DUST]!! * 4, mat.getTag(BaseMaterials.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_dust"), MaterialBlocks[mat, BaseMaterials.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getTag(BaseMaterials.DUST))
                    if (MaterialItems[mat, BaseMaterials.DUST] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_dust_from_block"), mat.name + "_dust", MaterialItems[mat, BaseMaterials.DUST]!! * 9, mat.getTag(BaseMaterials.BLOCK))
                }
            if (mat is GemMaterial)
                if (mat.hasTag(BaseMaterials.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, BaseMaterials.BLOCK]!!.toStack(), "II", "II", 'I', mat.getTag(BaseMaterials.GEM))
                    if (MaterialItems[mat, BaseMaterials.GEM] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_gem_from_block"), mat.name + "_gem", MaterialItems[mat, BaseMaterials.GEM]!! * 4, mat.getTag(BaseMaterials.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, BaseMaterials.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getTag(BaseMaterials.GEM))
                    if (MaterialItems[mat, BaseMaterials.GEM] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_gem_from_block"), mat.name + "_gem", MaterialItems[mat, BaseMaterials.GEM]!! * 9, mat.getTag(BaseMaterials.BLOCK))
                }
            if (mat is IngotMaterial) {
                if (mat.hasTag(BaseMaterials.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, BaseMaterials.BLOCK]!!.toStack(), "II", "II", 'I', mat.getTag(BaseMaterials.INGOT))
                    if (MaterialItems[mat, BaseMaterials.INGOT] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_ingot_from_block"), mat.name + "_ingot", MaterialItems[mat, BaseMaterials.INGOT]!! * 4, mat.getTag(BaseMaterials.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), MaterialBlocks[mat, BaseMaterials.BLOCK]!!.toStack(), "III", "III", "III", 'I', mat.getTag(BaseMaterials.INGOT))
                    if (MaterialItems[mat, BaseMaterials.INGOT] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_ingot_from_block"), mat.name + "_ingot", MaterialItems[mat, BaseMaterials.INGOT]!! * 9, mat.getTag(BaseMaterials.BLOCK))
                }
                if (MaterialItems[mat, BaseMaterials.INGOT] is IMaterialObject && MaterialItems.contains(mat, BaseMaterials.NUGGET))
                    RecipeMaker.addShapedRecipe(location(mat.name + "ingot_from_nuggets"), mat.name + "_ingot", MaterialItems[mat, BaseMaterials.INGOT]!!.toStack(), "NNN", "NNN", "NNN", 'N', mat.getTag(BaseMaterials.NUGGET))
                if (!mat.hasTag(BaseMaterials.DISABLE_SIMPLE_PROCESSING))
                    RecipeMaker.addFurnaceRecipe(location(mat.name + "_ingot"), mat.getTag(BaseMaterials.DUST), MaterialItems[mat, BaseMaterials.INGOT]!!)
                if (MaterialItems[mat, BaseMaterials.NUGGET] is IMaterialObject)
                    RecipeMaker.addShapelessRecipe(location(mat.name + "_nuggets"), MaterialItems[mat, BaseMaterials.NUGGET]!! * 9, mat.getTag(BaseMaterials.INGOT))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("pixellib", name)
}