package com.scientianovateam.versatile.common.serialization.registries

import com.google.gson.JsonObject
import com.scientianovateam.versatile.common.extensions.getArrayOrNull
import com.scientianovateam.versatile.common.extensions.getStringOrNull
import com.scientianovateam.versatile.common.extensions.json
import com.scientianovateam.versatile.common.extensions.toResLocV
import com.scientianovateam.versatile.common.serialization.IRegistrySerializer
import com.scientianovateam.versatile.materialsystem.properties.Property
import com.scientianovateam.versatile.velisp.convertToExpression
import com.scientianovateam.versatile.velisp.evaluated.BoolValue
import com.scientianovateam.versatile.velisp.evaluated.NullValue
import com.scientianovateam.versatile.velisp.types.ITypeHolder
import net.minecraft.util.ResourceLocation

object PropertySerializer : IRegistrySerializer<Property> {
    override fun read(json: JsonObject): Property {
        val name = ResourceLocation(json.getStringOrNull("namespace") ?: error("Property missing a namespace field"),
                json.getStringOrNull("name") ?: error("Property missing a name field"))
        val type = ITypeHolder.fromName(json.getStringOrNull("type") ?: error("Property $name missing type"))
        val default = json.get("default")?.run(::convertToExpression) ?: NullValue
        val valid = json.getArrayOrNull("valid")?.run(::convertToExpression) ?: BoolValue.TRUE
        return Property(name, type, default, valid)
    }

    override fun write(obj: Property) = json {
        "type" to obj.type.toString()
        "default" to obj.default.toJson()
        if (obj.valid != BoolValue.TRUE) "valid" to obj.valid.toJson()
    }
}