package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.extensions.times
import com.emosewapixel.pixellib.extensions.toStack
import com.emosewapixel.pixellib.materialsystem.addition.BaseObjTypes
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.main.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.main.Material
import com.emosewapixel.pixellib.materialsystem.properties.BlockCompaction
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

    private fun location(name: String) = ResourceLocation("pixellib", name)
}