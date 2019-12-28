package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.MaterialType

class MaterialValue(override val value: Material) : IEvaluated, IPropertyContainer {
    override val type = MaterialType
    override val properties = value.properties
}