package com.scientianovateam.versatile.common.serialization.registries

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getArrayOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.serialization.IRegistrySerializer
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.VELISPType
import com.scientianovateam.versatile.velisp.convertJSON
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.NullValue

object PropertySerializer : IRegistrySerializer<List<Property>> {
    override fun read(json: JsonObject): List<Property> = read(json, "")

    fun read(json: JsonObject, path: String): List<Property> {
        val result = mutableListOf<Property>()
        json.entrySet().forEach { (key, value) ->
            if (value is JsonObject) result.addAll(read(value, "$path$key/"))
        }
        if (json.has("name")) {
            val name = json.getStringOrNull("") ?: error("Property missing a name field")
            val type = VELISPType.fromName(json.getStringOrNull("type") ?: error("Property $name missing type"))
                    ?: error("Type for $name not found")
            val default = json.getArrayOrNull("default")?.run(::convertJSON) ?: NullValue
            val valid = json.getArrayOrNull("valid")?.run(::convertJSON) ?: BoolValue.TRUE
            result.add(Property(path + name, type, default, valid))
        }
        return result
    }

    override fun write(obj: List<Property>): JsonObject {
        TODO("not implemented")
    }
}