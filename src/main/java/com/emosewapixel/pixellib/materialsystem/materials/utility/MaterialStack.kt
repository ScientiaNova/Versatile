package com.emosewapixel.pixellib.materialsystem.materials.utility

import com.blamejared.crafttweaker.api.annotations.ZenRegister
import com.emosewapixel.pixellib.materialsystem.materials.Material
import org.openzen.zencode.java.ZenCodeType

//Material Stacks are ways of getting an amount of a certain Material
@ZenRegister
@ZenCodeType.Name("pixellib.materialsystem.materials.utility.MaterialStack")
data class MaterialStack @JvmOverloads constructor(@ZenCodeType.Field var material: Material, @ZenCodeType.Field var count: Int = 1) {
    val isEmpty: Boolean
        @ZenCodeType.Getter get() = count == 0
}