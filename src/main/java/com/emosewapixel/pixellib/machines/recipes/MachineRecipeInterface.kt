package com.emosewapixel.pixellib.machines.recipes

import com.emosewapixel.pixellib.extensions.nbt
import com.emosewapixel.pixellib.machines.BaseTileEntity
import com.emosewapixel.pixellib.machines.gui.BaseContainer
import com.emosewapixel.pixellib.machines.properties.ITEBoundProperty
import com.emosewapixel.pixellib.machines.properties.IValueProperty
import net.minecraft.nbt.CompoundNBT

open class MachineRecipeInterface(val recipeList: RecipeList, override val id: String, override val te: BaseTileEntity) : ITEBoundProperty {
    private val map = mutableMapOf<IRecipeComponent<out Any, out IValueProperty<out Any?>>, IValueProperty<out Any?>>()

    operator fun <P : IValueProperty<out Any?>> set(key: IRecipeComponent<out Any, P>, value: P) {
        map[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <P : IValueProperty<out Any?>> get(key: IRecipeComponent<out Any, P>) = map[key] as? P

    override fun detectAndSendChanges(container: BaseContainer) {}

    override fun createDefault() = MachineRecipeInterface(recipeList, id, te)

    override fun deserializeNBT(nbt: CompoundNBT?) {}

    override fun serializeNBT() = nbt { }
}