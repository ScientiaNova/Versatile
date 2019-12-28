package com.scientianovateam.versatile.materialsystem.properties

import com.scientianovateam.versatile.velisp.evaluated.IEvaluated

interface IPropertyContainer {
    val properties: Map<String, IEvaluated>
}