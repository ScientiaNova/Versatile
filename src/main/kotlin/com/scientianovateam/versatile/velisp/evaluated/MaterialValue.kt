package com.scientianovateam.versatile.velisp.evaluated

import com.scientianovateam.versatile.materialsystem.main.Material
import com.scientianovateam.versatile.velisp.MaterialType

class MaterialValue(override val value: Material) : IEvaluated {
    override val type = MaterialType
}