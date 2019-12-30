package com.scientianovateam.versatile.machines.gui.layout.components.slots

import com.scientianovateam.versatile.machines.gui.GUiUtils
import com.scientianovateam.versatile.machines.properties.implementations.items.TERecipeItemInputProperty
import com.scientianovateam.versatile.recipes.components.ingredients.items.input.ItemInputsComponent
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class RecipeItemSlotComponent(property: TERecipeItemInputProperty, slotIndex: Int) : ItemSlotComponent(property, slotIndex) {
    val recipeProperty = property.value.recipeProperty

    @OnlyIn(Dist.CLIENT)
    override fun drawItem(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        if (property.value.getStackInSlot(slotIndex).isEmpty)
            recipeProperty.value?.get(ItemInputsComponent::class.java)?.value?.getOrNull(slotIndex)?.let {
                val stack = it.stacks[(Minecraft.getInstance().world.gameTime / 40).toInt() % it.stacks.size]
                GUiUtils.drawTransparentItemStack(stack, xOffset + (width - 16) / 2 + x, yOffset + (height - 16) / 2 + y)
            }
    }
}