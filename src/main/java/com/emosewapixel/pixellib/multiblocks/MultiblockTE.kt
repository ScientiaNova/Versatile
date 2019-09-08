package com.emosewapixel.pixellib.multiblocks

import com.emosewapixel.pixellib.blocks.RotatableMachineBlock
import com.emosewapixel.pixellib.recipes.AbstractRecipeList
import com.emosewapixel.pixellib.recipes.SimpleMachineRecipe
import com.emosewapixel.pixellib.tiles.AbstractRecipeBasedTE
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import java.util.function.Predicate

//Multiblock TEs are TEs that require a multiblock structure to work
abstract class MultiblockTE<T : SimpleMachineRecipe>(type: TileEntityType<*>, recipeList: AbstractRecipeList<T, *>) : AbstractRecipeBasedTE<T>(type, recipeList) {
    protected lateinit var partList: List<IMultiblockPart>

    protected abstract val pattern: MultiblockPatternBuilder

    protected val isValidStructure: Boolean
        get() {
            val pattern = rotatedPattern
            val posInPattern = pattern.entries.filter { it.value.test(blockState) }.map { it.key }.first()

            partList = pattern.keys.filter { world!!.getTileEntity(it) is IMultiblockPart }.map { world!!.getTileEntity(it) as IMultiblockPart }

            return pattern.all { (key, value) -> value.test(world!!.getBlockState(getPos().subtract(posInPattern).add(key))) }
        }

    protected val rotatedPattern: Map<BlockPos, Predicate<BlockState>>
        get() {
            if (blockState.values.containsKey(RotatableMachineBlock.FACING)) {
                when (blockState.get(RotatableMachineBlock.FACING)) {
                    Direction.SOUTH -> return pattern.patternMap.entries.map { e -> Pair(BlockPos(e.key.x * -1, e.key.y, e.key.z * -1), e.value) }.toMap()
                    Direction.EAST -> return pattern.patternMap.entries.map { e -> Pair(BlockPos(e.key.z * -1, e.key.y, e.key.x), e.value) }.toMap()
                    Direction.WEST -> return pattern.patternMap.entries.map { e -> Pair(BlockPos(e.key.z, e.key.y, e.key.x * -1), e.value) }.toMap()
                }
            }
            return pattern.patternMap
        }

    protected fun selfPredicate() = Predicate<Block> { it === this.blockState.block }
}