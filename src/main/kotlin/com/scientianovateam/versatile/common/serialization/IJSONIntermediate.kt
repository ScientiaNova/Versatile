package com.scientianovateam.versatile.common.serialization

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated

interface IJSONIntermediate<T> {
    fun resolve(map: Map<String, IEvaluated> = emptyMap()): T
}