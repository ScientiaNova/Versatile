package com.scientianova.versatile.machines.gui.layout.components.stacksuppliers

import com.scientianova.versatile.common.extensions.isNotEmpty
import com.scientianova.versatile.common.extensions.shorten
import com.scientianova.versatile.machines.gui.layout.components.ITexturedGUIComponent
import com.scientianova.versatile.machines.gui.textures.BaseTextures
import com.scientianova.versatile.machines.recipes.Recipe
import com.scientianova.versatile.machines.recipes.components.grouping.IOType
import com.scientianova.versatile.machines.recipes.components.ingredients.fluids.FluidOutputsComponent
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidStack

open class RecipeOutputFluidStackSupplierSlot(val index: Int) : IStackSupplierComponent<FluidStack>, ITexturedGUIComponent {
    override var x = 0
    override var y = 0
    override val width = 18
    override val height = 18
    override var texture = BaseTextures.FLUID_SLOT
    override val ioType = IOType.OUTPUT
    override val type = FluidStack::class.java

    override fun getStacks(recipe: Recipe) = recipe[FluidOutputsComponent::class.java]?.value?.getOrNull(index)?.let { stack ->
        stack.value.stacks.filter(FluidStack::isNotEmpty).map { it.apply { orCreateTag.putFloat("output_chance", stack.chance) } }
    } ?: emptyList()

    override fun getExtraTooltips(stack: FluidStack) = if ("output_chance" in stack.orCreateTag) {
        val outputChance = stack.orCreateTag.getFloat("output_chance")
        if (outputChance < 1)
            listOf(TranslationTextComponent("extra_recipe_info.output_chance", (outputChance * 100).shorten()).string)
        else emptyList()
    } else emptyList()
}