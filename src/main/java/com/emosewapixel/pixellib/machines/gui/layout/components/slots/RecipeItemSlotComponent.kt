package com.emosewapixel.pixellib.machines.gui.layout.components.slots

import com.emosewapixel.pixellib.machines.gui.GUiUtils
import com.emosewapixel.pixellib.machines.properties.implementations.items.TERecipeItemInputProperty
import com.emosewapixel.pixellib.machines.recipes.components.ingredients.items.ItemInputsComponent
import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

open class RecipeItemSlotComponent(property: TERecipeItemInputProperty, slotIndex: Int) : ItemSlotComponent(property, slotIndex) {
    val recipeProperty = property.value.recipeProperty

    @OnlyIn(Dist.CLIENT)
    override fun drawItem(mouseX: Double, mouseY: Double, xOffset: Int, yOffset: Int, guiLeft: Int, guiTop: Int) {
        if (property.value.getStackInSlot(slotIndex).isEmpty)
            recipeProperty.value?.get(ItemInputsComponent::class.java)?.value?.getOrNull(slotIndex)?.let {
                val stacks = it.first.stacks
                val stack = stacks[(Minecraft.getInstance().world.gameTime / 40).toInt() % stacks.size]
                GUiUtils.drawTransparentItemStack(stack, xOffset + (width - 16) / 2 + x, yOffset + (height - 16) / 2 + y)
            }
    }
}