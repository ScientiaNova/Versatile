package com.scientianovateam.versatile.machines

import com.scientianovateam.versatile.machines.gui.layout.GUIBook
import com.scientianovateam.versatile.machines.properties.ITEBoundProperty
import net.minecraft.block.BlockState
import net.minecraft.world.IBlockReader
import net.minecraftforge.common.extensions.IForgeBlock

interface IMachineBlock : IForgeBlock {
    val teProperties: BaseTileEntity.() -> List<ITEBoundProperty>

    val guiLayout: BaseTileEntity.() -> GUIBook

    override fun hasTileEntity(state: BlockState?) = true

    override fun createTileEntity(state: BlockState?, world: IBlockReader?) = BaseTileEntity()
}