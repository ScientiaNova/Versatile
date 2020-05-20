package com.scientianova.versatile.materialsystem.properties

import com.scientianova.versatile.materialsystem.materials.Material

data class TransitionProperties(val neededAmount: Int, val to: () -> Material)