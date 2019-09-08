package com.emosewapixel.pixellib.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalBlock
import net.minecraft.block.material.Material
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.DirectionProperty
import net.minecraft.state.StateContainer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import java.util.function.Supplier

//Rotatable Machine Blocks are Blocks that have TEs that can be rotated horizontally
open class RotatableMachineBlock @JvmOverloads constructor (properties: Properties = Properties.create(Material.ROCK).hardnessAndResistance(3.5f), name: String, te: Supplier<TileEntity>, containerType: ContainerType<*>) : MachineBlock(properties, name, te, containerType) {
    init {
        this.defaultState = stateContainer.baseState.with(FACING, Direction.NORTH)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState = this.defaultState.with(FACING, context.placementHorizontalFacing.opposite)

    override fun rotate(state: BlockState, rotation: Rotation): BlockState = state.with(FACING, rotation.rotate(state.get(FACING)))

    override fun mirror(state: BlockState, mirror: Mirror): BlockState = state.rotate(mirror.toRotation(state.get(FACING)))

    override fun fillStateContainer(stateBuilder: StateContainer.Builder<Block, BlockState>) {
        stateBuilder.add(FACING)
    }

    companion object {
        val FACING: DirectionProperty = HorizontalBlock.HORIZONTAL_FACING
    }
}