package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonObject

interface IRegistrySerializer<T> : IJSONSerializer<T, JsonObject> {
    override fun read(json: JsonObject): T
    override fun write(obj: T): JsonObject
}