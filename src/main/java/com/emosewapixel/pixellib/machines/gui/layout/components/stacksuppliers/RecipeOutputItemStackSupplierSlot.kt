package com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.extensions.shorten
import com.emosewapixel.pixellib.machines.gui.layout.components.ITexturedGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.grouping.IOType
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemOutputsComponent
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent

open class RecipeOutputItemStackSupplierSlot(val index: Int) : IStackSupplierComponent<ItemStack>, ITexturedGUIComponent {
    override var x = 0
    override var y = 0
    override val width = 18
    override val height = 18
    override var texture = BaseTextures.ITEM_SLOT
    override val ioType = IOType.OUTPUT
    override val type = ItemStack::class.java

    override fun getStacks(recipe: Recipe) = recipe[ItemOutputsComponent::class.java]?.value?.getOrNull(index)?.let { stack ->
        stack.value.stacks.filter(ItemStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("output_chance", stack.chance) } }
    } ?: emptyList()

    override fun getExtraTooltips(stack: ItemStack) = if ("output_chance" in stack.orCreateTag) {
        val outputChance = stack.orCreateTag.getFloat("output_chance")
        if (outputChance < 1)
            listOf(TranslationTextComponent("extra_recipe_info.output_chance", (outputChance * 100).shorten()).string)
        else emptyList()
    } else emptyList()
}