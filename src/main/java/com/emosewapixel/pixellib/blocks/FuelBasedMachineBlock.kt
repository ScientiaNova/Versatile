package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.tiles.containers.providers.FueledMachineContainerProvider
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.RedstoneTorchBlock
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.container.ContainerType
import net.minecraft.particles.ParticleTypes
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.fml.network.NetworkHooks
import java.util.*
import java.util.function.Supplier

//Fueled Machine Blocks are Blocks that have TEs which consume fuel and can give off light
class FuelBasedMachineBlock @JvmOverloads constructor(properties: Properties = Properties.create(Material.ROCK).hardnessAndResistance(3.5f), name: String, te: Supplier<TileEntity>, containerType: ContainerType<*>) : RotatableMachineBlock(properties, name, te, containerType) {
    init {
        this.defaultState = stateContainer.baseState.with(LIT, false)
    }

    override fun getLightValue(state: BlockState) = if (state.get(LIT)) 13 else 0

    @OnlyIn(Dist.CLIENT)
    override fun animateTick(state: BlockState, world: World?, pos: BlockPos?, rand: Random?) {
        if (state.get(LIT)) {
            val x = pos!!.x.toDouble() + 0.5
            val y = pos.y.toDouble()
            val z = pos.z.toDouble() + 0.5
            if (rand!!.nextDouble() < 0.1)
                world!!.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0f, 1.0f, false)

            val facing = state.get(FACING)
            val axis = facing.axis
            val lvt_15_1_ = rand.nextDouble() * 0.6 - 0.3
            val lvt_17_1_ = if (axis === Direction.Axis.X) facing.xOffset.toDouble() * 0.52 else lvt_15_1_
            val lvt_19_1_ = rand.nextDouble() * 6.0 / 16.0
            val lvt_21_1_ = if (axis === Direction.Axis.Z) facing.zOffset.toDouble() * 0.52 else lvt_15_1_
            world!!.addParticle(ParticleTypes.SMOKE, x + lvt_17_1_, y + lvt_19_1_, z + lvt_21_1_, 0.0, 0.0, 0.0)
            world.addParticle(ParticleTypes.FLAME, x + lvt_17_1_, y + lvt_19_1_, z + lvt_21_1_, 0.0, 0.0, 0.0)
        }
    }

    override fun fillStateContainer(stateBuilder: StateContainer.Builder<Block, BlockState>) {
        stateBuilder.add(FACING, LIT)
    }

    override fun onBlockActivated(state: BlockState?, worldIn: World, pos: BlockPos?, player: PlayerEntity?, handIn: Hand?, hit: BlockRayTraceResult?): Boolean {
        if (!worldIn.isRemote)
            if (containerType != null)
                NetworkHooks.openGui((player as ServerPlayerEntity?)!!, FueledMachineContainerProvider(pos, registryName!!, containerType), pos)
        return true
    }

    companion object {
        val LIT: BooleanProperty = RedstoneTorchBlock.LIT
    }
}