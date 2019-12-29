package com.scientianovateam.versatile.common.serialization

import com.google.gson.JsonElement

interface IGeneralSerializer<T, J : JsonElement> : IIntermediateJSONSerializer<T, J>, IPacketSerializer<T>