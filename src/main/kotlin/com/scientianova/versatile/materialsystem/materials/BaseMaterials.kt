@file:JvmName("BaseMaterials")

package com.scientianova.versatile.materialsystem.materials

import com.scientianova.versatile.materialsystem.elements.carbon
import com.scientianova.versatile.materialsystem.elements.goldElem
import com.scientianova.versatile.materialsystem.elements.ironElem
import com.scientianova.versatile.materialsystem.events.DeferredMatRegister
import com.scientianova.versatile.materialsystem.forms.*
import com.scientianova.versatile.materialsystem.properties.*
import net.minecraft.block.Blocks
import net.minecraft.fluid.Fluids
import net.minecraft.item.ArmorMaterial
import net.minecraft.item.ItemTier
import net.minecraft.item.Items

internal val matReg = DeferredMatRegister()

val brick by matReg.ingot("brick") {
    set(tier) { 1 }
    set(color) { 0xb55c42 }
    set(blockCompaction) { BlockCompaction.FROM_2X2 }
    set(isAlloy) { false }
    set(malleable) { false }
}
val netherBrick by matReg.ingot("nether_brick") {
    set(tier) { 1 }
    set(color) { 0x472a30 }
    set(blockCompaction) { BlockCompaction.FROM_2X2 }
    set(isAlloy) { false }
    set(malleable) { false }
}
val iron by matReg.ingot("iron") {
    set(tier) { 1 }
    set(textureSet) { rough }
    set(element) { ironElem }
    set(unrefinedColor) { 0x947664 }
    set(itemTier) { ItemTier.IRON }
    set(armorMat) { ArmorMaterial.IRON }
    set(liquidTemp) { 1538 }
    set(hasOre) { true }
}
val gold by matReg.ingot("gold") {
    set(tier) { 2 }
    set(color) { 0xfad64a }
    set(textureSet) { shiny }
    set(element) { goldElem }
    set(itemTier) { ItemTier.GOLD }
    set(armorMat) { ArmorMaterial.GOLD }
    set(liquidTemp) { 1064 }
    set(hasOre) { true }
}
val coal by matReg.gem("coal") {
    set(color) { 0x1a1a1a }
    set(textureSet) { fuel }
    set(baseBurnTime) { 1600 }
    set(element) { carbon }
    set(hasOre) { true }
}
val charcoal by matReg.gem("charcoal") {
    set(color) { 0x443e33 }
    set(textureSet) { fuel }
    set(baseBurnTime) { 1600 }
    set(element) { carbon }
}
val flint by matReg.gem("flint") {
    set(color) { 0x222020 }
    set(textureSet) { sharp }
    set(blockCompaction) { BlockCompaction.NONE }
}
val lapis by matReg.gem("lapis") {
    set(color) { 0x2351be }
    set(hasOre) { true }
}
val quartz by matReg.gem("quartz") {
    set(color) { 0xe8dfd0 }
    set(textureSet) { crystal }
    set(blockCompaction) { BlockCompaction.FROM_2X2 }
    set(hasOre) { true }
}
val diamond by matReg.gem("diamond") {
    set(tier) { 2 }
    set(color) { 0x34ebe3 }
    set(textureSet) { pentagonal }
    set(element) { carbon }
    set(itemTier) { ItemTier.DIAMOND }
    set(armorMat) { ArmorMaterial.DIAMOND }
    set(hasOre) { true }
}
val emerald by matReg.gem("emerald") {
    set(tier) { 2 }
    set(color) { 0x08ad2c }
    set(textureSet) { octagonal }
    set(hasOre) { true }
}
val wood by matReg.dust("wooden") {
    set(tier) { -1 }
    set(color) { 0x87672c }
    set(textureSet) { fine }
    set(baseBurnTime) { 200 }
    set(itemTier) { ItemTier.WOOD }
}
val stone by matReg.dust("stone") {
    set(color) { 0xb1b0ae }
    set(textureSet) { fine }
    set(itemTier) { ItemTier.STONE }
}
val bone by matReg.dust("bone") {
    set(color) { 0xfcfaed }
    set(rodOutputCount) { 3 }
}
val blaze by matReg.dust("blaze") {
    set(color) { 0xffc20c }
    set(rodOutputCount) { 3 }
}
val redstone by matReg.dust("redstone") {
    set(tier) { 1 }
    set(color) { 0xfc1a19 }
    set(hasOre) { true }
}
val glowstone by matReg.dust("glowstone") {
    set(tier) { 1 }
    set(color) { 0xfcbe60 }
    set(blockCompaction) { BlockCompaction.FROM_2X2 }
}
val obsidian by matReg.dust("obsidian") {
    set(tier) { 1 }
    set(color) { 0x3c2a53 }
    set(textureSet) { fine }
}
val water by matReg.liquid("water") {
    set(color) { 0x3e4ac6 }
    set(textureSet) { fluid }
    set(liquidTemp) { 300 }
    set(gasNames) { listOf("steam") }
    set(gasTemp) { 373 }
    set(gasColor) { -1 }
}
val lava by matReg.liquid("lava") {
    set(color) { 0xc54c13 }
    set(textureSet) { fluid }
}

fun addVanilla() {
    item[coal, gemForm] = { Items.COAL }
    block[coal, blockForm] = { Blocks.COAL_BLOCK }
    block[coal, oreForm] = { Blocks.COAL_ORE }

    item[iron, nuggetForm] = { Items.IRON_NUGGET }
    item[iron, ingotForm] = { Items.IRON_INGOT }
    block[iron, blockForm] = { Blocks.IRON_BLOCK }
    block[iron, oreForm] = { Blocks.IRON_ORE }

    item[gold, nuggetForm] = { Items.GOLD_NUGGET }
    item[gold, ingotForm] = { Items.GOLD_INGOT }
    block[gold, blockForm] = { Blocks.GOLD_BLOCK }
    block[gold, oreForm] = { Blocks.GOLD_ORE }

    item[lapis, gemForm] = { Items.LAPIS_LAZULI }
    block[lapis, blockForm] = { Blocks.LAPIS_BLOCK }
    block[lapis, oreForm] = { Blocks.LAPIS_ORE }

    item[diamond, gemForm] = { Items.DIAMOND }
    block[diamond, blockForm] = { Blocks.DIAMOND_BLOCK }
    block[diamond, oreForm] = { Blocks.DIAMOND_ORE }

    item[emerald, gemForm] = { Items.EMERALD }
    block[emerald, blockForm] = { Blocks.EMERALD_BLOCK }
    block[emerald, oreForm] = { Blocks.EMERALD_ORE }

    item[quartz, gemForm] = { Items.QUARTZ }
    block[quartz, blockForm] = { Blocks.QUARTZ_BLOCK }
    block[quartz, oreForm] = { Blocks.NETHER_QUARTZ_ORE }

    item[redstone, dustForm] = { Items.REDSTONE }
    block[redstone, blockForm] = { Blocks.REDSTONE_BLOCK }
    block[redstone, oreForm] = { Blocks.REDSTONE_ORE }

    item[glowstone, dustForm] = { Items.GLOWSTONE_DUST }
    block[glowstone, blockForm] = { Blocks.GLOWSTONE }

    item[brick, ingotForm] = { Items.BRICK }
    block[brick, blockForm] = { Blocks.BRICKS }

    item[netherBrick, ingotForm] = { Items.NETHER_BRICK }
    block[netherBrick, blockForm] = { Blocks.NETHER_BRICKS }

    item[flint, gemForm] = { Items.FLINT }

    item[bone, dustForm] = { Items.BONE_MEAL }
    block[bone, blockForm] = { Blocks.BONE_BLOCK }

    item[blaze, dustForm] = { Items.BLAZE_POWDER }

    item[water, liquidForm] = { Items.WATER_BUCKET }
    block[water, liquidForm] = { Blocks.WATER }
    stillFluid[water, liquidForm] = { Fluids.WATER }
    flowingFluid[water, liquidForm] = { Fluids.FLOWING_WATER }

    item[lava, liquidForm] = { Items.LAVA_BUCKET }
    block[lava, liquidForm] = { Blocks.LAVA }
    stillFluid[lava, liquidForm] = { Fluids.LAVA }
    flowingFluid[lava, liquidForm] = { Fluids.FLOWING_LAVA }
}