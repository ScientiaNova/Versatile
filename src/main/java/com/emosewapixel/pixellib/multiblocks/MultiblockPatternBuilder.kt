package com.emosewapixel.pixellib.multiblocks

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import java.util.*
import java.util.function.Predicate
import kotlin.collections.HashMap

//The Multiblock Pattern Builder is simply a Builder for creating multiblock patterns
class MultiblockPatternBuilder {
    private val pattern = ArrayList<List<String>>()
    private val blockMap = HashMap<Char, Predicate<BlockState>>()

    lateinit var finalPattern: List<List<List<Predicate<BlockState>>>>
        private set

    var patternMap: HashMap<BlockPos, Predicate<BlockState>> = hashMapOf()
        private set

    fun addLayer(vararg layer: String): MultiblockPatternBuilder {
        pattern.add(listOf(*layer))
        return this
    }

    fun repeat(amount: Int): MultiblockPatternBuilder {
        repeat(amount) { pattern.add(pattern[pattern.size - 1]) }
        return this
    }

    fun where(c: Char, p: Predicate<BlockState>): MultiblockPatternBuilder {
        blockMap[c] = p
        return this
    }

    fun build(): MultiblockPatternBuilder {
        finalPattern = pattern.map { it.map { s -> s.map(blockMap::get).filterNotNull() } }

        for ((x, l1) in finalPattern.withIndex())
            for ((y, l2) in l1.withIndex())
                for ((z, pred) in l2.withIndex())
                    patternMap[BlockPos(x, y, z)] = pred

        return this
    }
}