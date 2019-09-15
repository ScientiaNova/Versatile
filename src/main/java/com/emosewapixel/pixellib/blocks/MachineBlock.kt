package com.emosewapixel.pixellib.blocks

import com.emosewapixel.pixellib.tiles.AbstractRecipeBasedTE
import com.emosewapixel.pixellib.tiles.containers.providers.MachineContainerProvider
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraftforge.common.ToolType
import net.minecraftforge.fml.network.NetworkHooks
import java.util.function.Supplier

//Machine Blocks are Blocks that have Tes
open class MachineBlock @JvmOverloads constructor(properties: Properties = Properties.create(Material.ROCK).hardnessAndResistance(3.5f), name: String, private var te: Supplier<TileEntity>, protected val containerType: ContainerType<*>?) : Block(properties) {
    init {
        setRegistryName(name)
    }

    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity? = te.get()

    override fun getHarvestTool(state: BlockState?): ToolType = ToolType.PICKAXE

    override fun harvestBlock(worldIn: World, player: PlayerEntity, pos: BlockPos, state: BlockState, te: TileEntity?, stack: ItemStack) {
        if (te is AbstractRecipeBasedTE<*>)
            te.dropInventory()
        super.harvestBlock(worldIn, player, pos, state, te, stack)
    }

    override fun onReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, p_196243_5_: Boolean) {
        if (state.block !== newState.block) {
            val te = world.getTileEntity(pos)
            if (te is AbstractRecipeBasedTE<*>)
                world.updateComparatorOutputLevel(pos, this)

            super.onReplaced(state, world, pos, newState, p_196243_5_)
        }
    }

    override fun onBlockActivated(state: BlockState?, worldIn: World, pos: BlockPos?, player: PlayerEntity?, handIn: Hand?, hit: BlockRayTraceResult?): Boolean {
        if (!worldIn.isRemote)
            if (containerType != null)
                NetworkHooks.openGui((player as ServerPlayerEntity?)!!, MachineContainerProvider(pos, registryName!!, containerType), pos)
        return true
    }

    override fun hasComparatorInputOverride(state: BlockState?) = true

    override fun getComparatorInputOverride(state: BlockState?, world: World, pos: BlockPos?) = Container.calcRedstone(world.getTileEntity(pos!!))

    override fun getRenderType(state: BlockState?): BlockRenderType = BlockRenderType.MODEL
}