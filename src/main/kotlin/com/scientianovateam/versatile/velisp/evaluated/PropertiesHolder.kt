package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.NothingType

data class PropertiesHolder(override val properties: Map<String, IEvaluated>) : IEvaluated, IPropertyContainer {
    override val value = properties
    override val type = NothingType
}