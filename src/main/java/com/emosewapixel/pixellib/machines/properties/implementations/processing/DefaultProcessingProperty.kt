package com.emosewapixel.pixellib.machines.properties.implementations.processing

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import net.minecraft.nbt.CompoundNBT

open class DefaultProcessingProperty(override val id: String, override val te: BaseTileEntity, val recipeProperty: RecipeProperty) : ITEBoundProperty {
    override fun detectAndSendChanges(container: BaseContainer) {}

    override fun createDefault() = this

    override fun deserializeNBT(nbt: CompoundNBT?) {}

    override fun serializeNBT() = nbt {}

    protected val processingHandlers by lazy {
        recipeProperty.recipeList.recipeComponents.values.mapNotNull { it.getProcessingHandler(te) }
    }

    override fun tick() {
        if (te.world?.isRemote != false) return
        recipeProperty.value?.let { recipe ->
            if (processingHandlers.all { it.isProcessing(recipe) }) {
                if (processingHandlers.all { it.shouldFinishProcessing(recipe) })
                    processingHandlers.forEach { it.finishProcessing(recipe) }
                else if (processingHandlers.all { it.canContinueProcessing(recipe) })
                    processingHandlers.forEach { it.processTick(recipe) }
            }
        }
    }

    override fun update() {
        if (te.world?.isRemote != false) return
        val recipe = recipeProperty.value ?: return
        if (processingHandlers.any { !it.isProcessing(recipe) } && processingHandlers.all { it.canStartProcessing(recipe) })
            processingHandlers.forEach { it.startProcessing(recipe) }
    }
}