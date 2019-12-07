package com.emosewapixel.pixellib.machines.capabilities.items

import net.minecraftforge.items.IItemHandler

interface IContainerItemHandler : IItemHandler {
    fun extraItemFromContainer(slot: Int, amount: Int, simulate: Boolean) = extractItem(slot, amount, simulate)
}