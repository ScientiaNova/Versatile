package com.scientianovateam.versatile.data.generation

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.scientianovateam.versatile.Versatile
import com.scientianovateam.versatile.data.block.BlockMaterialProvider
import com.scientianovateam.versatile.data.block.BlockProvider
import com.scientianovateam.versatile.data.block.SoundTypeProvider
import com.scientianovateam.versatile.data.form.FormPropertyProvider
import com.scientianovateam.versatile.data.item.ArmorTierProvider
import com.scientianovateam.versatile.data.item.ItemProvider
import com.scientianovateam.versatile.data.item.ToolTierProvider
import com.scientianovateam.versatile.data.material.ElementProvider
import com.scientianovateam.versatile.data.material.MaterialPropertyProvider
import com.scientianovateam.versatile.data.recipe.RecipeListProvider
import net.minecraft.data.DataGenerator
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent

@Mod.EventBusSubscriber(modid = Versatile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object DataRegistry {
    @SubscribeEvent
    fun gatherData(e: GatherDataEvent) = e.generator.providers {
        addSoundTypes()
        addBlockMaterials()
        addTiers()
        addElements()
        addMatProperties()
        addFormProperties()
    }
}

val GSON: Gson = GsonBuilder().serializeNulls().setPrettyPrinting().disableHtmlEscaping().create()

fun DataGenerator.providers(function: DataGenerator.() -> Unit) = this.function()
fun DataGenerator.soundTypes(function: SoundTypeProvider.() -> Unit) = addProvider(SoundTypeProvider(this).apply(function))
fun DataGenerator.blockMaterials(function: BlockMaterialProvider.() -> Unit) = addProvider(BlockMaterialProvider(this).apply(function))
fun DataGenerator.blocks(function: BlockProvider.() -> Unit) = addProvider(BlockProvider(this).apply(function))
fun DataGenerator.toolTiers(function: ToolTierProvider.() -> Unit) = addProvider(ToolTierProvider(this).apply(function))
fun DataGenerator.armorTiers(function: ArmorTierProvider.() -> Unit) = addProvider(ArmorTierProvider(this).apply(function))
fun DataGenerator.items(function: ItemProvider.() -> Unit) = addProvider(ItemProvider(this).apply(function))
fun DataGenerator.elements(function: ElementProvider.() -> Unit) = addProvider(ElementProvider(this).apply(function))
fun DataGenerator.matProperties(function: MaterialPropertyProvider.() -> Unit) = addProvider(MaterialPropertyProvider(this).apply(function))
fun DataGenerator.formProperties(function: FormPropertyProvider.() -> Unit) = addProvider(FormPropertyProvider(this).apply(function))
fun DataGenerator.recipeLists(function: RecipeListProvider.() -> Unit) = addProvider(RecipeListProvider(this).apply(function))