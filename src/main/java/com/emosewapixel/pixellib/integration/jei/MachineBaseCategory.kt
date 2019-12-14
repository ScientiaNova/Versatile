package com.emosewapixel.pixellib.integration.jei

import com.emosewapixel.pixellib.extensions.toStack
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.gui.layout.OffsetGUIComponent
import com.emosewapixel.pixellib.machines.gui.layout.components.stacksuppliers.IStackSupplierComponent
import com.emosewapixel.pixellib.machines.properties.implementations.recipes.RecipeProperty
import com.emosewapixel.pixellib.machines.recipes.Recipe
import com.emosewapixel.pixellib.machines.recipes.RecipeList
import com.emosewapixel.pixellib.machines.recipes.components.grouping.IOType
import mezz.jei.api.constants.VanillaTypes
import mezz.jei.api.gui.IRecipeLayout
import mezz.jei.api.gui.drawable.IDrawable
import mezz.jei.api.helpers.IGuiHelper
import mezz.jei.api.ingredients.IIngredients
import mezz.jei.api.recipe.category.IRecipeCategory
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextFormatting
import net.minecraft.util.text.TranslationTextComponent
import net.minecraftforge.fluids.FluidStack


@Suppress("UNCHECKED_CAST")
open class MachineBaseCategory(helper: IGuiHelper, protected val recipeList: RecipeList) : IRecipeCategory<Recipe> {
    val listGroup = recipeList.createComponentGroup()

    private val background: IDrawable = helper.createBlankDrawable(listGroup.width, listGroup.height)

    private val icon = recipeList.blocksImplementing.firstOrNull()?.let { helper.createDrawableIngredient(it.toStack()) }

    override fun getUid() = recipeList.name

    override fun getTitle(): String = recipeList.localizedName.string

    override fun getBackground() = background

    override fun getIcon() = icon

    override fun setIngredients(recipe: Recipe, ingredients: IIngredients) {
        val ingredientTable = recipe.page.unwrap().map(OffsetGUIComponent::component).filterIsInstance<IStackSupplierComponent<*>>()
                .groupBy(IStackSupplierComponent<*>::ioType).mapValues { it.value.groupBy(IStackSupplierComponent<*>::type) }

        ingredientTable[IOType.INPUT]?.let { typesMap ->
            typesMap[ItemStack::class.java]?.let { components ->
                ingredients.setInputLists(VanillaTypes.ITEM, components.map { (it as IStackSupplierComponent<ItemStack>).getStacks(recipe) })
            }
            typesMap[FluidStack::class.java]?.let { components ->
                ingredients.setInputLists(VanillaTypes.FLUID, components.map { (it as IStackSupplierComponent<FluidStack>).getStacks(recipe) })
            }
        }

        ingredientTable[IOType.OUTPUT]?.let { typesMap ->
            typesMap[ItemStack::class.java]?.let { components ->
                ingredients.setOutputLists(VanillaTypes.ITEM, components.map { (it as IStackSupplierComponent<ItemStack>).getStacks(recipe) })
            }
            typesMap[FluidStack::class.java]?.let { components ->
                ingredients.setOutputLists(VanillaTypes.FLUID, components.map { (it as IStackSupplierComponent<FluidStack>).getStacks(recipe) })
            }
        }
    }

    override fun setRecipe(layout: IRecipeLayout, recipe: Recipe, ingredients: IIngredients) {
        val guiItemStacks = layout.itemStacks
        val guiFluidStacks = layout.fluidStacks
        val itemInputs = ingredients.getInputs(VanillaTypes.ITEM)
        val fluidInputs = ingredients.getInputs(VanillaTypes.FLUID)
        val itemOutputs = ingredients.getOutputs(VanillaTypes.ITEM)
        val fluidOutputs = ingredients.getOutputs(VanillaTypes.FLUID)

        val supplierComponents = recipe.page.unwrap().filter { it.component is IStackSupplierComponent<*> }
        val ingredientMap = supplierComponents.groupBy { (it.component as IStackSupplierComponent<*>).type }
                .mapValues { entry -> entry.value.filter { (it.component as IStackSupplierComponent<*>).ioType != IOType.NONE } }

        val xOffset = (listGroup.width - recipe.page.width) / 2
        val yOffset = (listGroup.height - recipe.page.height) / 2

        var itemInputIndex = 0

        ingredientMap[ItemStack::class.java]?.forEachIndexed { index, component ->
            val supplierComponent = component.component as IStackSupplierComponent<ItemStack>
            guiItemStacks.init(index,
                    supplierComponent.ioType == IOType.INPUT,
                    component.xOffset + xOffset + supplierComponent.x,
                    component.yOffset + yOffset + supplierComponent.y
            )
            guiItemStacks.set(index, if (supplierComponent.ioType == IOType.INPUT) itemInputs[itemInputIndex++] else itemOutputs[index - itemInputIndex])
        }

        var fluidInputIndex = 0

        ingredientMap[FluidStack::class.java]?.forEachIndexed { index, component ->
            val supplierComponent = component.component as IStackSupplierComponent<FluidStack>
            guiFluidStacks.init(index,
                    supplierComponent.ioType == IOType.INPUT,
                    GUIFluidRenderer,
                    component.xOffset + xOffset + supplierComponent.x,
                    component.yOffset + yOffset + supplierComponent.y,
                    18, 18, 1, 1
            )
            guiFluidStacks.set(index, if (supplierComponent.ioType == IOType.INPUT) fluidInputs[fluidInputIndex++] else fluidOutputs[index - fluidInputIndex])
        }

        val specialComponents = supplierComponents.groupBy(Any::javaClass).map { it.value.first() }
                .map { (it.component as IStackSupplierComponent<*>) }.groupBy(IStackSupplierComponent<*>::type)

        guiItemStacks.addTooltipCallback { _, isInput, stack, tooltips ->
            specialComponents[ItemStack::class.java]?.fold(tooltips) { acc, component ->
                acc += (component as IStackSupplierComponent<ItemStack>).getExtraTooltips(stack)
                acc
            }
            if (!isInput && (Minecraft.getInstance().gameSettings.advancedItemTooltips || Screen.hasShiftDown()))
                tooltips.add(TextFormatting.DARK_GRAY.toString() + TranslationTextComponent("jei.tooltip.recipe.id", recipe.name).string)
        }
        guiFluidStacks.addTooltipCallback { _, isInput, stack, tooltips ->
            specialComponents[FluidStack::class.java]?.fold(tooltips) { acc, component ->
                acc += (component as IStackSupplierComponent<FluidStack>).getExtraTooltips(stack)
                acc
            }
            if (!isInput && (Minecraft.getInstance().gameSettings.advancedItemTooltips || Screen.hasShiftDown()))
                tooltips.add(TextFormatting.DARK_GRAY.toString() + TranslationTextComponent("jei.tooltip.recipe.id", recipe.name).string)
        }
    }

    override fun draw(recipe: Recipe, mouseX: Double, mouseY: Double) {
        recipe.page.drawInBackground(mouseX, mouseY, (listGroup.width - recipe.page.width) / 2, (listGroup.height - recipe.page.height) / 2)
        if (recipeList.recipeTransferFunction != null)
            ((Minecraft.getInstance().player.openContainer as? BaseContainer)?.te?.teProperties?.get("recipe") as? RecipeProperty)?.let { recipeProperty ->
                if (recipeProperty.recipeList === recipeList) {
                    TransferButton.drawInBackground(mouseX, mouseY, listGroup.width + 6, listGroup.height - 13)
                    TransferButton.drawInForeground(mouseX, mouseY, listGroup.width + 6, listGroup.height - 13)
                }
            }
    }

    override fun handleClick(recipe: Recipe, mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        recipeList.recipeTransferFunction?.let {
            if (TransferButton.isSelected(mouseX - (listGroup.width + 6), mouseY - (listGroup.height - 13))) {
                Minecraft.getInstance().currentScreen?.onClose()
                it.invoke(recipe, Minecraft.getInstance().player.openContainer as BaseContainer)
                return true
            }
        }
        return false
    }

    override fun getRecipeClass() = Recipe::class.java
}