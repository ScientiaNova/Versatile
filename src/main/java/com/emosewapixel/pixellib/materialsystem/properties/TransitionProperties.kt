package com.emosewapixel.pixellib.materialsystem.properties

import com.emosewapixel.pixellib.materialsystem.main.Material

data class TransitionProperties(val neededAmount: Int, val to: () -> Material)