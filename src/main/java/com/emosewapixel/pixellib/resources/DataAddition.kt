package com.emosewapixel.pixellib.resources

import com.emosewapixel.pixellib.materialsystem.MaterialRegistry
import com.emosewapixel.pixellib.materialsystem.lists.MaterialBlocks
import com.emosewapixel.pixellib.materialsystem.lists.MaterialFluids
import com.emosewapixel.pixellib.materialsystem.lists.MaterialItems
import com.emosewapixel.pixellib.materialsystem.lists.Materials
import com.emosewapixel.pixellib.materialsystem.materials.DustMaterial
import com.emosewapixel.pixellib.materialsystem.materials.GemMaterial
import com.emosewapixel.pixellib.materialsystem.materials.IMaterialObject
import com.emosewapixel.pixellib.materialsystem.materials.IngotMaterial
import net.minecraft.block.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.ResourceLocation

//This claass is used for registering the default data added by Pixel Lib
object DataAddition {
    fun register() {
        //Tags
        MaterialItems.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        MaterialBlocks.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        MaterialFluids.all.filterIsInstance<IMaterialObject>().forEach { TagMaps.addMatItemToTag(it) }
        TagMaps.addItemToTag("gems/coal", Items.COAL)
        TagMaps.addItemToTag("gems/charcoal", Items.CHARCOAL)
        TagMaps.addItemToTag("gems", Items.COAL)
        TagMaps.addItemToTag("gems", Items.CHARCOAL)
        TagMaps.addBlockToTag("storage_blocks/brick", Blocks.BRICKS)
        TagMaps.addBlockToTag("storage_blocks/nether_brick", Blocks.NETHER_BRICKS)
        TagMaps.addBlockToTag("storage_blocks/bone", Blocks.BONE_BLOCK)
        TagMaps.addBlockToTag("storage_blocks", Blocks.BRICKS)
        TagMaps.addBlockToTag("storage_blocks", Blocks.NETHER_BRICKS)
        TagMaps.addBlockToTag("storage_blocks", Blocks.BONE_BLOCK)
        TagMaps.addItemToTag("dusts/bone", Items.BONE_MEAL)
        TagMaps.addItemToTag("dusts/blaze", Items.BLAZE_POWDER)
        TagMaps.addItemToTag("dusts", Items.BONE_MEAL)
        TagMaps.addItemToTag("dusts", Items.BLAZE_POWDER)

        //Recipes
        Materials.all.filterIsInstance<DustMaterial>().forEach { mat ->
            val hasMaterialBlock = MaterialBlocks[mat, MaterialRegistry.BLOCK] is IMaterialObject
            if (mat.javaClass == DustMaterial::class.java)
                if (mat.hasTag(MaterialRegistry.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), output = ItemStack(MaterialBlocks[mat, MaterialRegistry.BLOCK]!!), shape = *arrayOf("II", "II", 'I', mat.getTag(MaterialRegistry.DUST)))
                    if (MaterialItems[mat, MaterialRegistry.DUST] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_dust_from_block"), mat.name + "_dust", ItemStack(MaterialItems.get(mat, MaterialRegistry.DUST)!!, 4), mat.getTag(MaterialRegistry.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_dust"), output = ItemStack(MaterialBlocks[mat, MaterialRegistry.BLOCK]!!), shape = *arrayOf("III", "III", "III", 'I', mat.getTag(MaterialRegistry.DUST)))
                    if (MaterialItems[mat, MaterialRegistry.DUST] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_dust_from_block"), mat.name + "_dust", ItemStack(MaterialItems.get(mat, MaterialRegistry.DUST)!!, 9), mat.getTag(MaterialRegistry.BLOCK))
                }
            if (mat is GemMaterial)
                if (mat.hasTag(MaterialRegistry.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), output = ItemStack(MaterialBlocks[mat, MaterialRegistry.BLOCK]!!), shape = *arrayOf("II", "II", 'I', mat.getTag(MaterialRegistry.GEM)))
                    if (MaterialItems[mat, MaterialRegistry.GEM] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_gem_from_block"), mat.name + "_gem", ItemStack(MaterialItems.get(mat, MaterialRegistry.GEM)!!, 4), mat.getTag(MaterialRegistry.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), output = ItemStack(MaterialBlocks[mat, MaterialRegistry.BLOCK]!!), shape = *arrayOf("III", "III", "III", 'I', mat.getTag(MaterialRegistry.GEM)))
                    if (MaterialItems[mat, MaterialRegistry.GEM] is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_gem_from_block"), mat.name + "_gem", ItemStack(MaterialItems.get(mat, MaterialRegistry.GEM)!!, 9), mat.getTag(MaterialRegistry.BLOCK))
                }
            if (mat is IngotMaterial) {
                if (mat.hasTag(MaterialRegistry.BLOCK_FROM_4X4)) {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), output = ItemStack(MaterialBlocks[mat, MaterialRegistry.BLOCK]!!), shape = *arrayOf("II", "II", 'I', mat.getTag(MaterialRegistry.INGOT)))
                    if (MaterialItems.get(mat, MaterialRegistry.INGOT) is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_ingot_from_block"), mat.name + "_ingot", ItemStack(MaterialItems.get(mat, MaterialRegistry.INGOT)!!, 4), mat.getTag(MaterialRegistry.BLOCK))
                } else {
                    if (hasMaterialBlock)
                        RecipeMaker.addShapedRecipe(location(mat.name + "_block"), output = ItemStack(MaterialBlocks[mat, MaterialRegistry.BLOCK]!!), shape = *arrayOf("III", "III", "III", 'I', mat.getTag(MaterialRegistry.INGOT)))
                    if (MaterialItems.get(mat, MaterialRegistry.INGOT) is IMaterialObject || hasMaterialBlock)
                        RecipeMaker.addShapelessRecipe(location(mat.name + "_ingot_from_block"), mat.name + "_ingot", ItemStack(MaterialItems.get(mat, MaterialRegistry.INGOT)!!, 9), mat.getTag(MaterialRegistry.BLOCK))
                }
                if (MaterialItems.get(mat, MaterialRegistry.INGOT) is IMaterialObject && MaterialItems.contains(mat, MaterialRegistry.NUGGET))
                    RecipeMaker.addShapedRecipe(location(mat.name + "ingot_from_nuggets"), mat.name + "_ingot", ItemStack(MaterialItems.get(mat, MaterialRegistry.INGOT)!!), shape = *arrayOf("NNN", "NNN", "NNN", 'N', mat.getTag(MaterialRegistry.NUGGET)))
                if (!mat.hasTag(MaterialRegistry.DISABLE_SIMPLE_PROCESSING))
                    RecipeMaker.addFurnaceRecipe(location(mat.name + "_ingot"), mat.getTag(MaterialRegistry.DUST), MaterialItems.get(mat, MaterialRegistry.INGOT)!!)
                if (MaterialItems.get(mat, MaterialRegistry.NUGGET) is IMaterialObject)
                    RecipeMaker.addShapelessRecipe(location(mat.name + "_nuggets"), output = ItemStack(MaterialItems.get(mat, MaterialRegistry.NUGGET)!!, 9), inputs = *arrayOf(mat.getTag(MaterialRegistry.INGOT)))
            }
        }
    }

    private fun location(name: String) = ResourceLocation("pixellib", name)
}