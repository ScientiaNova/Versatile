package com.emosewapixel.pixellib.machines.properties.implementations.processing

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import net.minecraft.nbt.CompoundNBT

open class AutomationProcessingProperty(override val id: String, override val te: BaseTileEntity, val recipeProperty: RecipeProperty) : ITEBoundProperty {
    override fun detectAndSendChanges(container: BaseContainer) {}

    override fun clone() = this

    override fun deserializeNBT(nbt: CompoundNBT?) {}

    override fun serializeNBT() = nbt {}

    protected val processingHandlers by lazy {
        recipeProperty.recipeList.recipeComponents.values.mapNotNull { it.getProcessingHandler(te) }
    }

    protected var isFinishing = false

    override fun tick() {
        if (te.world?.isRemote != false) return
        recipeProperty.value?.let { recipe ->
            if (processingHandlers.all(IProcessingHandler::isProcessing)) {
                if (processingHandlers.all { it.shouldFinishProcessing(recipe) }) {
                    isFinishing = true
                    processingHandlers.forEach { it.finishProcessingAutomation(recipe) }
                    isFinishing = false
                    startProcessing(recipe)
                } else if (processingHandlers.all { it.canContinueProcessing(recipe) })
                    processingHandlers.forEach { it.processTick(recipe) }
            }
        }
    }

    override fun update() {
        if (isFinishing) return
        if (te.world?.isRemote != false) return
        val recipe = recipeProperty.value ?: return
        if (processingHandlers.all { it.isProcessing() }) return
        startProcessing(recipe)
    }

    private fun startProcessing(recipe: Recipe) {
        if (processingHandlers.all { it.canStartProcessingAutomation(recipe) })
            processingHandlers.forEach { it.startProcessingAutomation(recipe) }
    }
}