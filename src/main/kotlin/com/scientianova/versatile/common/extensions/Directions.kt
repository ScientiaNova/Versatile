package com.scientianova.versatile.common.extensions

import net.minecraft.util.Direction

val Direction?.isHorizontal get() = this in Direction.Plane.HORIZONTAL
val Direction?.isVertical get() = this in Direction.Plane.VERTICAL