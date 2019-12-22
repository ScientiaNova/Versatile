package com.scientianovateam.versatile.machines.gui.layout.components.stacksuppliers

import com.scientianovateam.versatile.common.extensions.isNotEmpty
import com.scientianovateam.versatile.common.extensions.shorten
import com.scientianovateam.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianovateam.versatile.machines.gui.textures.BaseTextures
import com.scientianovateam.versatile.machines.recipes.Recipe
import com.scientianovateam.versatile.machines.recipes.components.grouping.IOType
import com.scientianovateam.versatile.machines.recipes.components.ingredients.items.ItemInputsComponent
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TranslationTextComponent

open class RecipeInputItemStackSupplierSlot(val index: Int) : IStackSupplierComponent<ItemStack>, ITexturedGUIComponent {
    override var x = 0
    override var y = 0
    override val width = 18
    override val height = 18
    override var texture = BaseTextures.ITEM_SLOT
    override val ioType = IOType.INPUT
    override val type = ItemStack::class.java

    override fun getStacks(recipe: Recipe) = recipe[ItemInputsComponent::class.java]?.value?.getOrNull(index)?.let { stack ->
        stack.value.stacks.filter(ItemStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("consume_chance", stack.chance) } }
    } ?: emptyList()

    override fun getExtraTooltips(stack: ItemStack) = if ("consume_chance" in stack.orCreateTag) {
        val consumeChance = stack.orCreateTag.getFloat("consume_chance")
        when {
            consumeChance <= 0 -> listOf(TranslationTextComponent("extra_recipe_info.not_consumed").string)
            consumeChance < 1 -> listOf(TranslationTextComponent("extra_recipe_info.consume_chance", (consumeChance * 100).shorten()).string)
            else -> emptyList()
        }
    } else emptyList()
}