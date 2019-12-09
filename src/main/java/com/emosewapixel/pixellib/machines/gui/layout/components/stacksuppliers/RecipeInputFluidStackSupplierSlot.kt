package com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers

import com.emosewapixel.pixellib.extensions.isNotEmpty
import com.emosewapixel.pixellib.extensions.shorten
import com.emosewapixel.pixellib.machines.gui.layout.components.ITexturedGUIComponent
import com.emosewapixel.pixellib.machines.gui.textures.BaseTextures
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.components.grouping.IOType
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.fluids.FluidInputsComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidStack

open class RecipeInputFluidStackSupplierSlot(val index: Int) : IStackSupplierComponent<FluidStack>, ITexturedGUIComponent {
    override var x = 0
    override var y = 0
    override val width = 18
    override val height = 18
    override var texture = BaseTextures.FLUID_SLOT
    override val ioType = IOType.INPUT
    override val type = FluidStack::class.java

    override fun getStacks(recipe: Recipe) = recipe[FluidInputsComponent::class.java]?.value?.getOrNull(index)?.let { pair ->
        pair.first.stacks.filter(FluidStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("consume_chance", pair.second) } }
    } ?: emptyList()

    override fun getExtraTooltips(stack: FluidStack) = if ("consume_chance" in stack.orCreateTag) {
        val consumeChance = stack.orCreateTag.getFloat("consume_chance")
        when {
            consumeChance <= 0 -> listOf(TranslationTextComponent("extra_recipe_info.not_consumed").string)
            consumeChance < 1 -> listOf(TranslationTextComponent("extra_recipe_info.consume_chance", (consumeChance * 100).shorten()).string)
            else -> emptyList()
        }
    } else emptyList()
}