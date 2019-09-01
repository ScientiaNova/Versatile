package com.EmosewaPixel.pixellib.blocks

import com.EmosewaPixel.pixellib.tiles.PoweredTE
import com.EmosewaPixel.pixellib.tiles.containers.providers.MachineContainerProvider
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.container.ContainerType
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.fml.network.NetworkHooks
import java.util.function.Supplier

//Activatable Machine Blocks are Blocks that can TEs which can have an active state
open class ActivatableMachineBlock @JvmOverloads constructor(properties: Properties = Properties.create(Material.ROCK).hardnessAndResistance(3.5f), name: String, te: Supplier<TileEntity>, containerType: ContainerType<*>) : RotatableMachineBlock(properties, name, te, containerType) {
    init {
        this.defaultState = stateContainer.baseState.with(ACTIVE, false)
    }

    override fun fillStateContainer(stateBuilder: StateContainer.Builder<Block, BlockState>) {
        stateBuilder.add(FACING, ACTIVE)
    }

    override fun onBlockActivated(state: BlockState?, worldIn: World, pos: BlockPos?, player: PlayerEntity?, handIn: Hand?, hit: BlockRayTraceResult?): Boolean {
        if (!worldIn.isRemote)
            if (worldIn.getTileEntity(pos!!) is PoweredTE)
                NetworkHooks.openGui((player as ServerPlayerEntity?)!!, MachineContainerProvider(pos, registryName!!, containerType!!), pos)
        return true
    }

    companion object {
        val ACTIVE: BooleanProperty = BooleanProperty.create("active")
    }
}