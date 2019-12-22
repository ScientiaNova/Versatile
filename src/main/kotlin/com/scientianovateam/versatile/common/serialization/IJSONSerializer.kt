package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement

interface IJSONSerializer<T, J : JsonElement> {
    fun read(json: J): T
    fun write(obj: T): J
}