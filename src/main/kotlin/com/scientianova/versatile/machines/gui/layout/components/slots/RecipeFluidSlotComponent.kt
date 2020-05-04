package com.scientianova.versatile.machines.gui.layout.components.slots

import com.scientianovateam.versatile.machines.gui.GUiUtils
import com.scientianovateam.versatile.machines.properties.implementations.fluids.TERecipeFluidInputProperty
import com.scientianovateam.versatile.machines.recipes.components.ingredients.fluids.FluidInputsComponent
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class RecipeFluidSlotComponent(property: TERecipeFluidInputProperty, tankIndex: Int) : FluidSlotComponent(property, tankIndex) {
    val recipeProperty = property.value.recipeProperty

    @OnlyIn(Dist.CLIENT)
    override fun drawInForeground(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        if (property.value.getFluidInTank(tankIndex).isEmpty)
            recipeProperty.value?.get(FluidInputsComponent::class.java)?.value?.getOrNull(tankIndex)?.let {
                val stack = it.stacks[(Minecraft.getInstance().world!!.gameTime / 40).toInt() % it.stacks.size]
                GUiUtils.drawTransparentFluidStack(stack, xOffset + x + 1, yOffset + y + 1, height - 2, width - 2)
            }
        super.drawInForeground(mouseX, mouseY, xOffset, yOffset, guiLeft, guiTop)
    }
}