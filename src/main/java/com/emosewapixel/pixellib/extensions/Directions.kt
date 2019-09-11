package com.emosewapixel.pixellib.extensions

import net.minecraft.util.Direction

private val horizontal = listOf(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH)

fun Direction?.isHorizontal() = this in horizontal