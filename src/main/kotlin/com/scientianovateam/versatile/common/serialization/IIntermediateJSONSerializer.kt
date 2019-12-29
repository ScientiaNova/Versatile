package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement

interface IIntermediateJSONSerializer<T, J : JsonElement> {
    fun read(json: J): IJSONIntermediate<T>
    fun write(obj: T): J
}