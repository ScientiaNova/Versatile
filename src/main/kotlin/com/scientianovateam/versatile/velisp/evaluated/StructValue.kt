package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.materialsystem.properties.IPropertyContainer
import com.scientianovateam.versatile.velisp.types.STRUCT

data class StructValue(override val properties: Map<String, IEvaluated>) : IEvaluated, IPropertyContainer {
    override val value = properties
    override val type = STRUCT
    override fun serialize() = json {
        properties.forEach { (key, value) ->
            key to value.serialize()
        }
    }

    override fun toJSON() = json {
        properties.forEach { (key, value) ->
            value.toJSON()?.let { key to it }
        }
    }
}